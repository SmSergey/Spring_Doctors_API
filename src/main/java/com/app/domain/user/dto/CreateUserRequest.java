package com.app.domain.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateUserRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
