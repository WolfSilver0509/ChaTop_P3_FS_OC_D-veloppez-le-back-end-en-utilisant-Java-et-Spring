/*
 * Classe de service responsable de la gestion des messages.
 * Elle inclut la création d'un message et son enregistrement dans la base de données.
 */
package com.p3_oc_fs.chatop.services;

import com.p3_oc_fs.chatop.dtos.CreateMessageResponseDto;
import com.p3_oc_fs.chatop.dtos.MessageDto;
import com.p3_oc_fs.chatop.models.Message;
import com.p3_oc_fs.chatop.models.Rental;
import com.p3_oc_fs.chatop.models.User;
import com.p3_oc_fs.chatop.repositorys.MessageRepository;
import com.p3_oc_fs.chatop.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserRepository userRepository;

    public CreateMessageResponseDto createMessage(MessageDto messageDto) {
        // Créer une nouvelle entité Message à partir du DTO
        Message message = new Message();
        message.setMessage(messageDto.getMessage());

        // Récupérer l'entité Rental en utilisant l'ID de location du DTO
        Optional<Rental> optionalRental = rentalService.findById(messageDto.getRental_id());

        if (optionalRental.isPresent()) {
            message.setRental_id(optionalRental.get());
        } else {
            // Gérer le cas où la location n'est pas trouvée (optionnel : lancer une exception ou enregistrer une erreur)
        }

        // Récupérer l'entité User en utilisant l'ID utilisateur du DTO
        Optional<User> optionalUser = userRepository.findById(messageDto.getUser_id());

        if (optionalUser.isPresent()) {
            message.setUser_id(optionalUser.get());
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé (optionnel : lancer une exception ou enregistrer une erreur)
        }

        // Enregistrer l'entité Message dans la base de données
        message = messageRepository.save(message);

        // Créer un objet CreateMessageResponse avec le message de succès
        CreateMessageResponseDto response = new CreateMessageResponseDto("Message send with success");

        return response;
    }
}
