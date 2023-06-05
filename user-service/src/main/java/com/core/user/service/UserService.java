package com.core.user.service;

import com.core.user.dto.ResponseUserDTO;
import com.core.user.dto.UpdateUserDTO;
import com.core.user.dto.UserDTO;
import com.core.user.entity.User;
import com.core.user.repository.UserDao;
import com.core.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;


    public User saveUser(UserDTO userDTO) {
        User user = User.builder().username(userDTO.getUsername())
                .fullName(userDTO.getFullName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        User saveUser = userRepository.save(user);
        ResponseUserDTO responseUserDTO = modelMapper.map(saveUser, ResponseUserDTO.class);
        userDao.saveUser(responseUserDTO);
        return saveUser;
    }

    public List<ResponseUserDTO> getUsers() {
//        return userRepository.findAll();
        return userDao.getAllUserFromRedis();
    }

    public ResponseUserDTO getUserByUserId(long id) {
        return userDao.getUserByUserId(id);
    }

    public String deleteUserById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return userDao.deleteUserById(id);
        } else {
            return "User not found";
        }
    }

    public User updateUserById(UpdateUserDTO updateUserDTO) {
        Optional<User> optionalUser = userRepository.findById(updateUserDTO.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(updateUserDTO.getUsername() != null ? updateUserDTO.getUsername() : user.getUsername());
            user.setFullName(updateUserDTO.getFullName() != null ? updateUserDTO.getFullName() : user.getFullName());
            user.setEmail(updateUserDTO.getEmail() != null ? updateUserDTO.getEmail() : user.getEmail());
            user.setPassword(
                    updateUserDTO.getPassword() != null ? passwordEncoder.encode(updateUserDTO.getPassword()) : user.getPassword()
            );
            user = userRepository.saveAndFlush(user);
            ResponseUserDTO responseUserDTO = modelMapper.map(user, ResponseUserDTO.class);
            userDao.updateUserById(responseUserDTO);
            return user;
        }
        return null;
    }


}
