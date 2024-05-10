package com.example.discussion.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comments {
    /** Identifiant unique du commentaire généré automatiquement ***/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Contenu du commentaire ***/
    @NotEmpty
    private String content;

    /** Utilisateur associé au commentaire ***/
    @ManyToOne
    private Users users;

    /** Post associé au commentaire ***/
    @ManyToOne
    private Posts posts;

    /** Constructeur avec tous les champs ***/
    public Comments(String content, Users user, Posts post) {
        this.content = content;
        this.users = user;
        this.posts = post;
    }
}
