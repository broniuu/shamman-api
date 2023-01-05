package com.example.toikprojekt2022.controler;

import com.example.toikprojekt2022.model.LoginRequest;
import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.dto.UserDto;
import com.example.toikprojekt2022.repository.UserRepository;
import com.example.toikprojekt2022.service.IUserService;
import com.example.toikprojekt2022.service.TokenService;
import com.example.toikprojekt2022.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Klasa obsługuje endpointy związane z użytkownikiem
 */
@RestController
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userService = new UserService(userRepository, passwordEncoder);
    }

    /**
     * Rejestracja użytkownika
     *
     * @param userDto               dane użytkonika do rejestracji
     * @return                      zarejestrowany użytkownik
     * @throws RuntimeException
     */
    @PostMapping(value ="/user/registration")
    public ResponseEntity<User> registerUserAccount(@RequestBody UserDto userDto) throws RuntimeException {
        User registered = userService.registerNewUserAccount(userDto);

        return new ResponseEntity<>(registered, HttpStatus.OK);
    }

    /**
     * Logowanie użytkownika
     *
     * @param userLogin                 dane do logowania użytkownika
     * @return                          zalogowany użytkownik
     * @throws AuthenticationException
     */
    @PostMapping(value ="/user/login")
    public String token(@RequestBody LoginRequest userLogin) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.login(), userLogin.password()));
        return tokenService.generateToken(authentication);
    }

    /**
     * Usuwanie konta użytkownika
     *
     * @param login     login do usuwanego konta
     * @return          usuwany użytkownik
     */
    @DeleteMapping(value = "{login}/user/delete")
    public ResponseEntity<User> deleteUserAccount(@PathVariable String login) {
        User deleted = userService.deleteUserAccount(login);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    /**
     * Aktualizowanie danych użytkownika
     *
     * @param login     login do aktualizowanego konta
     * @param userDto   dane do aktualizacji
     * @return          zaktualizowany użytkownik
     */
    @PostMapping(value = "{login}/user/update")
    public ResponseEntity<User> updateUserAccount(@PathVariable String login, @RequestBody UserDto userDto) {
        User updated = userService.updateUserAccount(login, userDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * Wyświetlenie danych użytkownika
     *
     * @param login     login do wyświetlanego konta
     * @return          wyświetlany użytkownik
     */
    @GetMapping(value = "{login}/user")
    public ResponseEntity<User> showUserAccount(@PathVariable String login) {
        User shown = userService.showUserAccount(login);
        return new ResponseEntity<>(shown, HttpStatus.OK);
    }
}
