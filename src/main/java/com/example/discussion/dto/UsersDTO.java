package com.example.discussion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Annotation Lombok pour générer automatiquement les constructeurs sans arguments, avec tous les arguments et les méthodes getter ***/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsersDTO {

    /** Nom d'utilisateur ***/
    private String username;

    /** Prénom de l'utilisateur ***/
    private String firstname;

    /** Nom de famille de l'utilisateur ***/
    private String lastname;

    /** Adresse e-mail de l'utilisateur ***/
    private String email;
}
