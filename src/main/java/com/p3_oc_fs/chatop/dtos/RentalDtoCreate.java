package com.p3_oc_fs.chatop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalDtoCreate {

    /* Identifiant de la location, généralement non utilisé lors de la création */
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

    /* Image de la location, envoyée lors de la création */
    private MultipartFile picture;

}
