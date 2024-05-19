// Controller Message Package
package com.p3_oc_fs.chatop.controllers;

import com.p3_oc_fs.chatop.dtos.MessageDto;
import com.p3_oc_fs.chatop.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.p3_oc_fs.chatop.dtos.CreateMessageResponseDto;

import java.security.Principal;

@RestController
@RequestMapping("api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * Endpoint pour créer un nouveau message.
     *
     * @param messageDto L'objet de transfert de données contenant les détails du message.
     * @return ResponseEntity<CreateMessageResponseDto> Un objet CreateMessageResponseDto contenant les détails du message créé.
     */
    @PostMapping
    public ResponseEntity<CreateMessageResponseDto> createMessage(@RequestBody MessageDto messageDto) {
        CreateMessageResponseDto response = messageService.createMessage(messageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
