package com.p3_oc_fs.chatop.controllers;

import com.p3_oc_fs.chatop.models.User;

import com.p3_oc_fs.chatop.dtos.LoginUserDto;
import com.p3_oc_fs.chatop.dtos.RegisterUserDto;
import com.p3_oc_fs.chatop.services.AuthentificationService;
import com.p3_oc_fs.chatop.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur pour les opérations d'authentification des utilisateurs.
 */
@RequestMapping("/api/auth")
@RestController
public class AuthentificationController {
    private final JwtService jwtService;
    private final AuthentificationService authenticationService;

    public AuthentificationController(JwtService jwtService, AuthentificationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    /**
     * Endpoint pour l'inscription d'un nouvel utilisateur.
     * @param registerUserDto Les informations d'inscription de l'utilisateur.
     * @return La réponse HTTP contenant les informations de l'utilisateur inscrit.
     */

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        String jwtToken = jwtService.generateToken(registeredUser);

        Map<String, String> response = new HashMap<>();
        response.put("token", jwtToken);

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint pour l'authentification d'un utilisateur existant.
     * @param loginUserDto Les informations de connexion de l'utilisateur.
     * @return La réponse HTTP contenant le token JWT d'authentification.
     */

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        Map<String, String> response = new HashMap<>();
        response.put("token", jwtToken);

        return ResponseEntity.ok(response);
    }
}

