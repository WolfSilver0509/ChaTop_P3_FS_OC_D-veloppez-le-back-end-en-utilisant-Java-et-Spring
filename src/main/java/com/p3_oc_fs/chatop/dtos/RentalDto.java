package com.p3_oc_fs.chatop.dtos;

import com.p3_oc_fs.chatop.models.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;

@Getter@Setter
@NoArgsConstructor @AllArgsConstructor
public class RentalDto {

    private String name;

    private BigDecimal surface;

    private BigDecimal price;

    private String description;

    private MultipartFile picture;

    private Integer ownerId;


    public RentalDto(BigDecimal price, String description, BigDecimal surface, String name) {
        this.price = price;
        this.description = description;
        this.surface = surface;
        this.name = name;
    }
}



