package com.core.user.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {

    private Long id;

    private String username;

    private String fullName;

    private String email;

    private String password;

}
