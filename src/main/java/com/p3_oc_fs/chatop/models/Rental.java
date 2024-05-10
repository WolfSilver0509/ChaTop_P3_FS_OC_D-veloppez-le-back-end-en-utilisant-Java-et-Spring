package com.p3_oc_fs.chatop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "rentals")
public class Rental {

    /* Identifiant de la location */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /* Nom de la location */
    private String name;

    /* Surface de la location */
    private BigDecimal surface;

    /* Prix de la location */
    private BigDecimal price;

    /* Chemin de l'image de la location */
    private String picture;

    /* Description de la location */
    @Column(length = 2000)
    private String description;

    /* Propriétaire de la location */
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner_id;

    /* Date de création de la location */
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date created_at;

    /* Date de mise à jour de la location */
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date updated_at;

    /* Constructeur */
    public Rental(String name, BigDecimal surface, BigDecimal price, String description, User owner_id) {
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.description = description;
        this.owner_id = owner_id;
    }
}
