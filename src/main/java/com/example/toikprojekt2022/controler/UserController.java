package com.example.toikprojekt2022.controler;

import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.dto.UserDto;
import com.example.toikprojekt2022.repository.UserRepository;
import com.example.toikprojekt2022.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = new UserService(userRepository, passwordEncoder);
    }
    @PostMapping(value ="/user/registration")
    public User registerUserAccount(@RequestBody UserDto userDto) throws RuntimeException {
        User registered = userService.registerNewUserAccount(userDto);
        return registered;
    }
}
