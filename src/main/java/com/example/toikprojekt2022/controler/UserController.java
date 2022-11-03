package com.example.toikprojekt2022.controler;

import com.example.toikprojekt2022.UserService;
import com.example.toikprojekt2022.dto.UserDto;
import com.example.toikprojekt2022.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserRepository userRepository) {
        this.userService = new UserService(userRepository);
    }

    @PostMapping(value = "/register")
    public UserDto reqisterUserAccount (@RequestBody UserDto userDto){
        try {
            userService.registerUser(userDto);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return userDto;
    }
}
