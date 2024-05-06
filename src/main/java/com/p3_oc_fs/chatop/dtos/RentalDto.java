package com.p3_oc_fs.chatop.dtos;

import com.p3_oc_fs.chatop.models.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;

@Getter@Setter
@NoArgsConstructor @AllArgsConstructor
public class RentalDto {

    private Integer id;

    private String name;

    private BigDecimal surface;

    private BigDecimal price;

    private String description;

    private MultipartFile picture;

    private Integer owner_id;

    private Date created_at;

    private Date updated_at;

    private String pictureUrl;


//    private Integer ownerId;


//    public RentalDto(BigDecimal price, String description, BigDecimal surface, String name) {
//        this.price = price;
//        this.description = description;
//        this.surface = surface;
//        this.name = name;
//    }


    public RentalDto(Integer id, String name, BigDecimal surface, BigDecimal price, String description, Integer owner_id, Date created_at, Date updated_at, String pictureUrl) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.description = description;
        this.owner_id = owner_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.pictureUrl = pictureUrl;


    }


}



