package com.p3_oc_fs.chatop.repositorys;

import com.p3_oc_fs.chatop.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface de dépôt pour la gestion des utilisateurs dans la base de données.
 * Cette interface hérite de la classe `CrudRepository` de Spring Data JPA,
 * qui fournit des méthodes CRUD (Create, Read, Update, Delete) génériques
 * pour effectuer des opérations sur les entités de type `User`.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Recherche un utilisateur par son adresse e-mail ou son id
     *
     * @param email L'adresse e-mail de l'utilisateur à rechercher.
     * @return Un objet `Optional<User>` contenant l'utilisateur trouvé
     *         ou un objet vide s'il n'est pas trouvé.
     */
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);
}
