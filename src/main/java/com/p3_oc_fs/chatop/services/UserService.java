package com.p3_oc_fs.chatop.services;

import com.p3_oc_fs.chatop.dtos.UserGetMe;
import com.p3_oc_fs.chatop.models.User;
import com.p3_oc_fs.chatop.repositorys.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des utilisateurs.
 * Cette classe contient les méthodes pour effectuer des opérations
 * sur les entités de type `User` dans la base de données.
 * Elle est annotée avec `@Service` pour être détectée automatiquement
 * par Spring et injectée dans les classes qui en dépendent.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    /**
     * Récupère la liste de tous les utilisateurs.
     * @return La liste de tous les utilisateurs.
     */
    public List<UserGetMe> allUsers() {
        List<UserGetMe> UserGetMes = new ArrayList<>();

        userRepository.findAll().forEach(user -> {
            UserGetMe UserGetMe = new UserGetMe(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
            UserGetMes.add(UserGetMe);
        });

        return UserGetMes;
    }

    /**
     * Récupère l'utilisateur courant.
     * @return L'utilisateur courant.
     */
    public UserGetMe getCurrentUser(User user) {
        return new UserGetMe(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }
}