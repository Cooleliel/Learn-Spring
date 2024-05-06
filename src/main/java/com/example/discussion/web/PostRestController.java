package com.example.discussion.web;

import com.example.discussion.entities.Post;
import com.example.discussion.repositories.PostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostRestController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @PostMapping("/post")
    public Post createPost(@RequestBody @Valid Post post){
        postRepository.save(post);
        return post;
    }

    @DeleteMapping("/post/{id}")
    public void deletePost(@PathVariable Long id){
        postRepository.deleteById(id);
    }
}
