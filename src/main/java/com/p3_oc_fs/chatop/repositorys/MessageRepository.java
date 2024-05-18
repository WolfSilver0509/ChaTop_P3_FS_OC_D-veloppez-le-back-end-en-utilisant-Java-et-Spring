package com.p3_oc_fs.chatop.repositorys;

import com.p3_oc_fs.chatop.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de dépôt pour la gestion des messages dans la base de données.
 * Cette interface hérite de la classe `JpaRepository` de Spring Data JPA,
 * qui fournit des méthodes CRUD (Create, Read, Update, Delete) génériques
 * pour effectuer des opérations sur les entités de type `Message`.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
