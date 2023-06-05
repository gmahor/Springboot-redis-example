package com.core.user.repository;

import com.core.user.dto.ResponseUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class UserDao {

    public static final String HASH_KEY = "Users";

    @Autowired
    private RedisTemplate template;

    public ResponseUserDTO saveUser(ResponseUserDTO responseUserDTO) {
        template.opsForHash().put(HASH_KEY,
                responseUserDTO.getId(),
                responseUserDTO);
        return responseUserDTO;
    }

    public List<ResponseUserDTO> getAllUserFromRedis() {
        return template.opsForHash().values(HASH_KEY);
    }

    public ResponseUserDTO getUserByUserId(long id) {
        return (ResponseUserDTO) template.opsForHash().get(HASH_KEY, id);
    }

    public String deleteUserById(long id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "user removed successfully";
    }

    public ResponseUserDTO updateUserById(ResponseUserDTO responseUserDTO) {
        template.opsForHash().put(HASH_KEY, responseUserDTO.getId(), responseUserDTO);
        log.info("User updated successfully in redis");
        return responseUserDTO;
    }


}
