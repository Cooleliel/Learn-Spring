package com.example.discussion.web;

import com.example.discussion.dto.ContentsDTO;
import com.example.discussion.dto.PostsDTO;
import com.example.discussion.entities.Posts;
import com.example.discussion.entities.Users;
import com.example.discussion.repositories.PostsRepository;
import com.example.discussion.repositories.UsersRepository;
import com.example.discussion.services.DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PostsRestController {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    /** Récupérer tous les posts de tous les utilisateurs ***/
    @GetMapping("posts")
    public ResponseEntity<List<PostsDTO>> getAllPosts() {
        try {
            List<PostsDTO> postsDTOS = postsRepository.findAll().stream()
                    .map(post -> discussionRepository.convertPostEntityToDTO(post))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(postsDTOS);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /** Récupérer tous les posts d'un utilisateur spécifique ***/
    @GetMapping("users/{userId}/posts")
    public ResponseEntity<List<PostsDTO>> getPostsByUserId(@PathVariable Long userId) {
        try {
            List<PostsDTO> postsDTOS = postsRepository.findByUsersId(userId).stream()
                    .map(post -> discussionRepository.convertPostEntityToDTO(post))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(postsDTOS);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** Récupérer un post spécifique par son ID et l'ID de l'utilisateur ***/
    @GetMapping("users/{userId}/posts/{postId}")
    public ResponseEntity<PostsDTO> getPostById(@PathVariable Long userId, @PathVariable Long postId) {
        try {
            Optional<Posts> specificPostOptional = postsRepository.findByUsersIdAndId(userId, postId);
            if (specificPostOptional.isPresent()) {
                Posts specificPost = specificPostOptional.get();
                return ResponseEntity.ok(discussionRepository.convertPostEntityToDTO(specificPost));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /** Créer un nouveau post pour un utilisateur spécifique ***/
    @PostMapping("users/{userId}/posts")
    public ResponseEntity<Boolean> createPost(@RequestBody ContentsDTO contentsDTO, @PathVariable Long userId) {
        try {
            Users user = usersRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("Utilisateur inexistant: " + userId));
            Posts post = discussionRepository.convertDTOToPostEntity(user, contentsDTO);
            postsRepository.save(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
