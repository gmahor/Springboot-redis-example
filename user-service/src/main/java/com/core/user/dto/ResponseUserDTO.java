package com.core.user.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("Users")
public class ResponseUserDTO implements Serializable {

    @Id
    private Long id;

    private String username;

    private String fullName;

    private String email;

    private String password;

}
