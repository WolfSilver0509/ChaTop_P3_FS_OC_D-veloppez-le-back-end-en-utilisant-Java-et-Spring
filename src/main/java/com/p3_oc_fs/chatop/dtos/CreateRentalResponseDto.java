package com.p3_oc_fs.chatop.dtos;

public class CreateRentalResponseDto {
    private final String message;

    public CreateRentalResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
