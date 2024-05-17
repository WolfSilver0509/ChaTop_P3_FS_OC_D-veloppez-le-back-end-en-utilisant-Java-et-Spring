package com.p3_oc_fs.chatop.dtos;

public class RegisterResponseDto {
    private final String token;

    public RegisterResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }


}
