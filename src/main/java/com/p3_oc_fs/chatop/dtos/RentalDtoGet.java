package com.p3_oc_fs.chatop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RentalDtoGet {
    private Integer id;

    private String name;

    private BigDecimal surface;

    private BigDecimal price;

    private String description;

    private Integer owner_id;

    private Date created_at;

    private Date updated_at;

    private String picture;

}

