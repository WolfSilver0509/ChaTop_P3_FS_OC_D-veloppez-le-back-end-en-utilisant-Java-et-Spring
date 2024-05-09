package com.p3_oc_fs.chatop.dtos;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * DTO (Data Transfer Object) représentant les informations d'un message.
 * Ce DTO est utilisé pour transférer les données des messages entre les différentes couches de l'application.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MessageDto {

    /* Identifiant du message */
    private Integer id;

    /* Identifiant de la location associée au message */
    private Integer rental_id;

    /* Identifiant de l'utilisateur qui a posté le message */
    private Integer user_id;

    /* Contenu du message */
    private String message;

    /* Date de création du message */
    private Date created_at;

    /* Date de mise à jour du message */
    private Date updated_at;


}
