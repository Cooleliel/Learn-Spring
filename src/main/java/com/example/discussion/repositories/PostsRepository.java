package com.example.discussion.repositories;

import com.example.discussion.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/** Interface qui étend JpaRepository pour l'entité Posts avec une clé primaire de type Long ***/
public interface PostsRepository extends JpaRepository<Posts, Long> {

    /** Méthode pour trouver une liste de posts par l'ID de l'utilisateur ***/
    List<Posts> findByUsersId(Long userId);

    /** Méthode pour trouver un post par l'ID de l'utilisateur et l'ID du post ***/
    Optional<Posts> findByUsersIdAndId(Long userId, Long postId);
}
