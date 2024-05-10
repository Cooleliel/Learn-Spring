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
public class Users {
    /** Identifiant unique de l'utilisateur généré automatiquement ***/
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nom d'utilisateur de l'utilisateur ***/
    @NotEmpty
    private String username;

    /** Prénom de l'utilisateur ***/
    @NotEmpty
    private String firstname;

    /** Nom de famille de l'utilisateur ***/
    @NotEmpty
    private String lastname;

    /** Adresse e-mail de l'utilisateur ***/
    @NotEmpty
    private String email;

    /** Liste des publications de l'utilisateur ***/
    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ignorer lors de la sérialisation JSON pour éviter une boucle infinie
    private Collection<Posts> postsList;

    /** Liste des commentaires de l'utilisateur ***/
    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ignorer lors de la sérialisation JSON pour éviter une boucle infinie
    private Collection<Comments> commentsList;

    /** Constructeur avec tous les champs ***/
    public Users(String username, String firstname, String lastname, String email, Collection<Posts> postsList, Collection<Comments> commentsList) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.postsList = postsList;
        this.commentsList = commentsList;
    }
}
