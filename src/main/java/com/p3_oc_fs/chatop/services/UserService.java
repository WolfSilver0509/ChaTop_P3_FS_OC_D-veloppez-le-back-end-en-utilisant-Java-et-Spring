package com.p3_oc_fs.chatop.services;

import com.p3_oc_fs.chatop.models.User;
import com.p3_oc_fs.chatop.repositorys.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service pour la gestion des utilisateurs.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
