package com.p3_oc_fs.chatop.dtos;

import com.p3_oc_fs.chatop.models.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

//    @ManyToOne
//    @JoinColumn(name = "owner_id", nullable = false)
//    private User owner_id;
    private Integer owner_id;

    private Date created_at;

    private Date updated_at;

    private String picture;

}

