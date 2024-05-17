package com.p3_oc_fs.chatop.controllers;

import com.p3_oc_fs.chatop.dtos.MessageDto;
import com.p3_oc_fs.chatop.models.Message;
import com.p3_oc_fs.chatop.models.Rental;
import com.p3_oc_fs.chatop.models.User;
import com.p3_oc_fs.chatop.repositorys.MessageRepository;
import com.p3_oc_fs.chatop.repositorys.UserRepository;
import com.p3_oc_fs.chatop.repositorys.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("api/messages")
    public class MessageController {

        @Autowired
        private MessageRepository messageRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RentalRepository rentalRepository;

        /**
         * Endpoint pour créer un nouveau message.
         *
         * @param messageDTO L'objet de transfert de données contenant les détails du message.
         *                   Champs requis : message, user_id, rental_id.
         * @return ResponseEntity<MessageDto> Un objet MessageDto contenant les détails du message créé.
         */
        @PostMapping
        public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto messageDTO) {
            Message message = new Message();
            message.setMessage(messageDTO.getMessage());

            // Récupère l'utilisateur par ID à partir de UserRepository
            User user = userRepository.findById(messageDTO.getUser_id()).orElse(null);
            message.setUser_id(user);

            // Récupère la location par ID à partir de RentalRepository
            Rental rental = rentalRepository.findById(messageDTO.getRental_id()).orElse(null);
            message.setRental_id(rental);

            // Enregistre le message dans MessageRepository
            message = messageRepository.save(message);

            // Convertit l'entité Message en MessageDto
            MessageDto createdMessageDto = new MessageDto();
            createdMessageDto.setId(message.getId());
            createdMessageDto.setMessage(message.getMessage());
            createdMessageDto.setUser_id(message.getUser_id().getId());
            createdMessageDto.setRental_id(message.getRental_id().getId());
            createdMessageDto.setCreated_at(message.getCreated_at());
            createdMessageDto.setUpdated_at(message.getUpdated_at());

            return new ResponseEntity<>(createdMessageDto, HttpStatus.CREATED);
        }
    }
    