package com.p3_oc_fs.chatop.dtos;

import com.p3_oc_fs.chatop.dtos.RentalDtoGet;

import java.util.List;

public class GetAllRentalsResponseDto {
    private final List<RentalDtoGet> rentals;

    public GetAllRentalsResponseDto(List<RentalDtoGet> rentals) {
        this.rentals = rentals;
    }

    public List<RentalDtoGet> getRentals() {
        return rentals;
    }
}

