package com.p3_oc_fs.chatop.configurations;

import com.p3_oc_fs.chatop.repositorys.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration de l'application.
 */
@Configuration
public class ApplicationConfiguration {
    private final UserRepository userRepository;

    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Définit un service pour récupérer les détails de l'utilisateur par son nom d'utilisateur (email).
     * @return Un service pour récupérer les détails de l'utilisateur.
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Définit un encodeur de mots de passe utilisant l'algorithme BCrypt.
     * @return Un encodeur de mots de passe BCrypt.
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Définit un gestionnaire d'authentification à partir de la configuration Spring Security.
     * @param config La configuration d'authentification.
     * @return Un gestionnaire d'authentification.
     * @throws Exception Si une erreur survient lors de la récupération du gestionnaire d'authentification.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Définit un fournisseur d'authentification utilisant un DAO pour récupérer les détails de l'utilisateur.
     * @return Un fournisseur d'authentification DAO.
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}

