package com.core.user.controller;

import com.core.user.dto.ResponseUserDTO;
import com.core.user.dto.UpdateUserDTO;
import com.core.user.dto.UserDTO;
import com.core.user.entity.User;
import com.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/saveUser")
    public ResponseEntity<Object> saveUser(@RequestBody UserDTO userDTO) {
        User user = userService.saveUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/getUsers")
    public ResponseEntity<Object> getUsers() {
        List<ResponseUserDTO> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/getUser/{id}")
    public ResponseEntity<Object> getUsers(@PathVariable(name = "id") long id) {
        ResponseUserDTO responseUserDTO = userService.getUserByUserId(id);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(name = "id") long id) {
        String message = userService.deleteUserById(id);
        if (message.equals("User not found")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @PutMapping(value = "/updateUser")
    public ResponseEntity<Object> getUserByUsername(@RequestBody UpdateUserDTO updateUserDTO) {
        User user = userService.updateUserById(updateUserDTO);
        if (user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
    }

}
