package com.example.discussion.web;

import com.example.discussion.dto.UsersDTO;
import com.example.discussion.entities.Users;
import com.example.discussion.repositories.UsersRepository;
import com.example.discussion.services.DiscussionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
public class UsersRestController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    /** Récupérer tous les utilisateurs ***/
    @GetMapping("/users")
    public ResponseEntity<List<UsersDTO>> getUsers() {
        try {
            /** Récupération de tous les utilisateurs depuis la base de données ***/
            List<UsersDTO> usersList = usersRepository.findAll().stream()
                    /** Conversion de chaque utilisateur en DTO à l'aide de DiscussionRepository ***/
                    .map(user -> discussionRepository.convertUserEntityToDTO(user))
                    .collect(Collectors.toList());
            /** Retourne une réponse OK avec la liste des utilisateurs ***/
            return ResponseEntity.ok(usersList);
        } catch (NoSuchElementException e) {
            /** Si une exception se produit, retourne une erreur interne du serveur ***/
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /** Récupérer un utilisateur par ID ***/
    @GetMapping("/users/{userId}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable Long userId) {
        try {
            /** Recherche de l'utilisateur par ID dans la base de données ***/
            Users user = usersRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("Utilisateur inexistant"));
            /** Retourne une réponse OK avec l'utilisateur trouvé ***/
            return ResponseEntity.ok(discussionRepository.convertUserEntityToDTO(user));
        } catch (NoSuchElementException e) {
            /** Si l'utilisateur n'est pas trouvé, retourne une réponse NotFound ***/
            return ResponseEntity.notFound().build();
        }
    }

    /** Créer un nouvel utilisateur ***/
    @PostMapping("/users")
    public ResponseEntity<Boolean> createUser(@RequestBody @Valid UsersDTO userDto) {
        try {
            /** Conversion du DTO en entité User ***/
            Users user = discussionRepository.convertDTOToUserEntity(userDto);
            /** Enregistrement de l'utilisateur dans la base de données ***/
            usersRepository.save(user);
            /** Retourne une réponse de création réussie ***/
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        } catch (NoSuchElementException e) {
            /** Si une exception se produit, retourne une erreur interne du serveur ***/
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
