package com.p3_oc_fs.chatop.services;

import com.p3_oc_fs.chatop.dtos.LoginUserDto;
import com.p3_oc_fs.chatop.dtos.RegisterUserDto;
import com.p3_oc_fs.chatop.models.User;
import com.p3_oc_fs.chatop.repositorys.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service pour l'authentification des utilisateurs.
 */
@Service
public class AuthentificationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthentificationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Inscrit un nouvel utilisateur.
     * @param input Les informations de l'utilisateur à inscrire.
     * @return L'utilisateur inscrit.
     */
    public User signup(RegisterUserDto input) {
        var user = new User()
                .setName(input.getName())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    /**
     * Authentifie un utilisateur.
     * @param input Les informations de l'utilisateur à authentifier.
     * @return L'utilisateur authentifié.
     * @throws BadCredentialsException Si les identifiants fournis sont invalides.
     */
    public User authenticate(LoginUserDto input) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

    /**
     * Récupère la liste de tous les utilisateurs.
     * @return La liste de tous les utilisateurs.
     */
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
