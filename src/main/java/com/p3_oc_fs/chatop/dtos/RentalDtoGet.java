package com.p3_oc_fs.chatop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RentalDtoGet {

    /* Identifiant de la location */
    private Integer id;

    /* Nom de la location */
    private String name;

    /* Surface de la location */
    private BigDecimal surface;

    /* Prix de la location */
    private BigDecimal price;

    /* Description de la location */
    private String description;

    /* Identifiant du propriétaire de la location */
    private Integer owner_id;

    /* Date de création de la location */
    private Date created_at;

    /* Date de mise à jour de la location */
    private Date updated_at;

    /* Chemin de l'image de la location */
    private String picture;

}
