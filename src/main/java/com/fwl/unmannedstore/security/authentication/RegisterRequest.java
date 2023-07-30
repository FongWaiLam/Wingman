package com.fwl.unmannedstore.security.authentication;

import com.fwl.unmannedstore.security.entity.Role;
import com.fwl.unmannedstore.security.entity.ValidRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
    @ValidRole
    private Role role;
}
