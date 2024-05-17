/*
 * Classe de service responsable de la gestion des messages.
 * Elle inclut la création d'un message et son enregistrement dans la base de données.
 */
package com.p3_oc_fs.chatop.services;

import com.p3_oc_fs.chatop.dtos.MessageDto; // Importation de la classe MessageDto
import com.p3_oc_fs.chatop.models.Message; // Importation de la classe Message (modèle)
import com.p3_oc_fs.chatop.models.Rental; // Importation de la classe Rental (modèle)
import com.p3_oc_fs.chatop.models.User; // Importation de la classe User (modèle)
import com.p3_oc_fs.chatop.repositorys.MessageRepository; // Importation du dépôt de messages
import com.p3_oc_fs.chatop.repositorys.UserRepository; // Importation du dépôt d'utilisateurs

import org.springframework.beans.factory.annotation.Autowired; // Importation de l'annotation Autowired pour l'injection de dépendances
import org.springframework.stereotype.Service; // Importation de l'annotation Service pour marquer cette classe comme un service

import java.util.Optional; // Importation de la classe Optional pour gérer les valeurs potentielles nulles

@Service // Indique que cette classe est un composant de service dans Spring
public class MessageService {

    @Autowired // Injection de dépendance pour MessageRepository
    private MessageRepository messageRepository;

    @Autowired // Injection de dépendance pour RentalService
    private RentalService rentalService;

    @Autowired // Injection de dépendance pour UserRepository
    private UserRepository userRepository;

    /*
     * Méthode pour créer un nouveau message.
     * Prend un MessageDto en entrée, le convertit en entité Message,
     * récupère les entités Rental et User associées, et enregistre l'entité Message.
     * Retourne un MessageDto contenant les détails du message enregistré.
     */
    public MessageDto createMessage(MessageDto messageDto) {
        // Créer une nouvelle entité Message à partir du DTO
        Message message = new Message();
        message.setMessage(messageDto.getMessage());

        // Récupérer l'entité Rental en utilisant l'ID de location du DTO
        Optional<Rental> optionalRental = rentalService.findById(messageDto.getRental_id());

        // Vérifier si l'entité Rental est trouvée
        if (optionalRental.isPresent()) {
            // Attribuer l'entité Rental trouvée au message
            message.setRental_id(optionalRental.get());
        } else {
            // Gérer le cas où la location n'est pas trouvée (optionnel : lancer une exception ou enregistrer une erreur)
        }

        // Récupérer l'entité User en utilisant l'ID utilisateur du DTO
        Optional<User> optionalUser = userRepository.findById(messageDto.getUser_id());

        // Vérifier si l'entité User est trouvée
        if (optionalUser.isPresent()) {
            // Attribuer l'entité User trouvée au message
            message.setUser_id(optionalUser.get());
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé (optionnel : lancer une exception ou enregistrer une erreur)
        }

        // Enregistrer l'entité Message dans la base de données
        message = messageRepository.save(message);

        // Créer un nouveau MessageDto pour retourner les détails du message enregistré
        MessageDto createdMessageDto = new MessageDto();
        createdMessageDto.setId(message.getId());
        createdMessageDto.setMessage(message.getMessage());
        createdMessageDto.setUser_id(message.getUser_id().getId());
        createdMessageDto.setRental_id(message.getRental_id().getId());
        createdMessageDto.setCreated_at(message.getCreated_at());
        createdMessageDto.setUpdated_at(message.getUpdated_at());

        // Retourner le MessageDto créé
        return createdMessageDto;
    }
}
