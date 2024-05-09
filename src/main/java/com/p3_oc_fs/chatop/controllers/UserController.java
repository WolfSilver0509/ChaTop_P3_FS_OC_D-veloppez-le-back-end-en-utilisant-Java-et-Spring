package com.p3_oc_fs.chatop.controllers;

import com.p3_oc_fs.chatop.dtos.UserGetMe;
import com.p3_oc_fs.chatop.models.User;
import com.p3_oc_fs.chatop.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur pour la gestion des utilisateurs.
 */
@RequestMapping("/api")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint pour récupérer les informations de l'utilisateur actuellement authentifié.
     * @param user L'utilisateur actuellement authentifié.
     * @return Les informations de l'utilisateur actuellement authentifié.
     */
    @GetMapping("/auth/me")
    public UserGetMe getCurrentUser(@AuthenticationPrincipal User user) {
        return userService.getCurrentUser(user);
    }

    /**
     * Endpoint pour récupérer la liste de tous les utilisateurs.
     * @return La réponse HTTP contenant la liste de tous les utilisateurs.
     */
    @GetMapping("/")
    public ResponseEntity<List<UserGetMe>> allUsers() {
        List<UserGetMe> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    /**
     * Endpoint pour récupérer les informations d'un utilisateur spécifique par son ID.
     * @param id L'ID de l'utilisateur.
     * @return Les informations de l'utilisateur spécifié.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<UserGetMe> getUserById(@PathVariable Integer id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserGetMe userGetMe = userService.getCurrentUser(user);
            return ResponseEntity.ok(userGetMe);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
