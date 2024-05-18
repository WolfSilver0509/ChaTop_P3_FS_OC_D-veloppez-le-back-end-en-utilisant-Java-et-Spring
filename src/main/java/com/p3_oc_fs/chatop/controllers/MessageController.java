// Controller Message Package
package com.p3_oc_fs.chatop.controllers;

import com.p3_oc_fs.chatop.dtos.MessageDto;
import com.p3_oc_fs.chatop.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * Endpoint pour créer un nouveau message.
     *
     * @param messageDTO L'objet de transfert de données contenant les détails du message.
     *                  Champs requis : message.
     * @return ResponseEntity<MessageDto> Un objet MessageDto contenant les détails du message créé.
     */
    @PostMapping
    public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto messageDTO) {
        MessageDto createdMessageDto = messageService.createMessage(messageDTO);
        return new ResponseEntity<>(createdMessageDto, HttpStatus.CREATED);
    }
}
