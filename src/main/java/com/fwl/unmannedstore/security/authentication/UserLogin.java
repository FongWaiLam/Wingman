package com.fwl.unmannedstore.security.authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLogin {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}