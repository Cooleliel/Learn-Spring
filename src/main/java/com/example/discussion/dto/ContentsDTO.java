package com.example.discussion.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Annotation Lombok pour générer automatiquement les constructeurs sans arguments, avec tous les arguments et les méthodes getter/setter ***/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContentsDTO {

    /** Contenu ***/
    @NotEmpty
    private String content;
}
