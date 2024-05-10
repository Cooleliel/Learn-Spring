package com.example.discussion.repositories;

import com.example.discussion.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/** Interface qui étend JpaRepository pour l'entité Comments avec une clé primaire de type Long ***/
public interface CommentsRepository extends JpaRepository<Comments, Long> {

    /** Méthode pour trouver une liste de commentaires par l'ID de l'utilisateur ***/
    List<Comments> findByUsersId(Long userId);

    /** Méthode pour trouver une liste de commentaires par l'ID de l'utilisateur et l'ID du post ***/
    Optional<List<Comments>> findByUsersIdAndPostsId(Long userId, Long postId);

    /** Méthode pour trouver un commentaire par l'ID de l'utilisateur, l'ID du post et l'ID du commentaire ***/
    Optional<Comments> findByUsersIdAndPostsIdAndId(Long userId, Long postId, Long commentId);
}
