package com.example.discussion.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Posts {
    /** Identifiant unique du post généré automatiquement ***/
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Contenu du post ***/
    @NotEmpty
    private String content;

    /** Utilisateur associé au post ***/
    @ManyToOne
    private Users users;

    /** Liste des commentaires associés au post ***/
    @OneToMany(mappedBy = "posts", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ignorer lors de la sérialisation JSON pour éviter une boucle infinie
    private Collection<Comments> commentsList;

    /** Constructeur avec tous les champs ***/
    public Posts(String content, Users users, Collection<Comments> commentsList) {
        this.content = content;
        this.users = users;
        this.commentsList = commentsList;
    }
}
