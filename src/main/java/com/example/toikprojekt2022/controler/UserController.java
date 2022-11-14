package com.example.toikprojekt2022.controler;

import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.dto.UserDto;
import com.example.toikprojekt2022.repository.UserRepository;
import com.example.toikprojekt2022.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = new UserService(userRepository, passwordEncoder);
    }
    @PostMapping(value ="/user/registration")
    public ResponseEntity<User> registerUserAccount(@RequestBody UserDto userDto) throws RuntimeException {
        User registered = userService.registerNewUserAccount(userDto);
        return new ResponseEntity<>(registered, HttpStatus.OK);
    }
}
