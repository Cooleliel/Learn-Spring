package com.example.discussion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Annotation Lombok pour générer automatiquement les constructeurs sans arguments, avec tous les arguments et les méthodes getter/setter **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentsDTO {

    /** Contenu du commentaire ***/
    private String content;

    /** Auteur du commentaire ***/
    private String author;

    /** Post auquel le commentaire est associé ***/
    private String post;
}
