package com.p3_oc_fs.chatop.models;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

    /* Identifiant du message */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

   /* Identifiant de la location */
    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    /* Identifiant de l'utilisateur */
    private User user_id;

    /* Contenu du message */
    private String message;

    /* Date de création du message */
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private java.util.Date created_at;

    /* Date de mise à jour du message */
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private java.util.Date updated_at;

}
