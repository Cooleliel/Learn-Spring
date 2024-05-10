package com.example.discussion.web;

import com.example.discussion.dto.ContentsDTO;
import com.example.discussion.entities.Comments;
import com.example.discussion.entities.Posts;
import com.example.discussion.entities.Users;
import com.example.discussion.repositories.CommentsRepository;
import com.example.discussion.repositories.PostsRepository;
import com.example.discussion.repositories.UsersRepository;
import com.example.discussion.services.DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CommentsRestController {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    /** Récupérer tous les commentaires ***/
    @GetMapping("comments")
    public ResponseEntity<List<ContentsDTO>> getAllComments() {
        try {
            List<ContentsDTO> commentsList = commentsRepository.findAll()
                    .stream()
                    .map(comment -> discussionRepository.convertCommentEntityToDTO(comment))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(commentsList);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** Récupérer les commentaires d'un utilisateur ***/
    @GetMapping("users/{userId}/comments")
    public ResponseEntity<List<ContentsDTO>> getCommentsByUserId(@PathVariable Long userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            List<ContentsDTO> contentsDTOs = user.getCommentsList().stream()
                    .map(comment -> discussionRepository.convertCommentEntityToDTO(comment))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(contentsDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /** Récupérer les commentaires d'un utilisateur pour une publication spécifique ***/
    @GetMapping("users/{userId}/posts/{postId}/comments")
    public ResponseEntity<List<ContentsDTO>> getCommentsByUserIdAndPostId(@PathVariable Long userId, @PathVariable Long postId) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<ContentsDTO> contentsDTOs = postsRepository.findByUsersIdAndId(userId, postId)
                    .map(post -> post.getCommentsList().stream()
                            .map(comment -> discussionRepository.convertCommentEntityToDTO(comment))
                            .collect(Collectors.toList()))
                    .orElse(new ArrayList<>());
            return ResponseEntity.ok(contentsDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /** Récupérer un commentaire spécifique par ID, ID d'utilisateur et ID de publication ***/
    @GetMapping("users/{userId}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ContentsDTO> getCommentByIdAndUsersIdAndPostsId(@PathVariable Long userId, @PathVariable Long postId, @PathVariable Long commentId) {
        Optional<Comments> commentOptional = commentsRepository.findByUsersIdAndPostsIdAndId(userId, postId, commentId);
        if (commentOptional.isPresent()) {
            ContentsDTO contentsDTO = discussionRepository.convertCommentEntityToDTO(commentOptional.get());
            return ResponseEntity.ok(contentsDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /** Créer un commentaire pour un utilisateur et une publication spécifiques **/
    @PostMapping("users/{userId}/posts/{postId}/comments")
    public ResponseEntity<Boolean> createComment(@RequestBody ContentsDTO contentsDTO, @PathVariable Long userId, @PathVariable Long postId) {
        try {
            Users user = usersRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Utilisateur inexistant: " + userId));
            Posts post = postsRepository.findByUsersIdAndId(userId, postId).orElseThrow(() -> new NoSuchElementException("Post inexistant : " + postId));
            Comments comments = discussionRepository.convertDTOToCommentEntity(user, post, contentsDTO);
            commentsRepository.save(comments);
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
