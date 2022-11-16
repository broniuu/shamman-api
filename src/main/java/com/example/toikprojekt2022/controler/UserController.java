package com.example.toikprojekt2022.controler;

import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.dto.UserDto;
import com.example.toikprojekt2022.repository.UserRepository;
import com.example.toikprojekt2022.service.IUserService;
import com.example.toikprojekt2022.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class UserController {
    private final IUserService userService;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = new UserService(userRepository, passwordEncoder);
    }
    @PostMapping(value ="/user/registration")
    public ResponseEntity<User> registerUserAccount(@RequestBody UserDto userDto) throws RuntimeException {
        User registered = userService.registerNewUserAccount(userDto);
        return new ResponseEntity<>(registered, HttpStatus.OK);
    }
    @DeleteMapping(value = "{login}/user/delete")
    public ResponseEntity<User> deleteUserAccount(@PathVariable String login) {
        User deleted = userService.deleteUserAccount(login);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
    @PostMapping(value = "{login}/user/update")
    public ResponseEntity<User> updateUserAccount(@PathVariable String login, @RequestBody UserDto userDto) {
        User updated = userService.updateUserAccount(login, userDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
    @GetMapping(value = "{login}/user")
    public ResponseEntity<User> showUserAccount(@PathVariable String login) {
        User shown = userService.showUserAccount(login);
        return new ResponseEntity<>(shown, HttpStatus.OK);
    }
}
