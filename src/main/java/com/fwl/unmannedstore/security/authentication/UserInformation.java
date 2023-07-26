package com.fwl.unmannedstore.security.authentication;

import com.fwl.unmannedstore.security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInformation {
    private Integer id;
    private String username;
    private String email;
    private Role role;
}
