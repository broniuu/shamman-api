package com.example.toikprojekt2022.controler;

import com.example.toikprojekt2022.dto.LoginRequest;
import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.dto.UserDto;
import com.example.toikprojekt2022.repository.UserRepository;
import com.example.toikprojekt2022.service.TokenService;
import com.example.toikprojekt2022.service.UserService;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.example.toikprojekt2022.security.Utilities.checkUser;

/**
 * Klasa obsługuje endpointy związane z użytkownikiem
 */
@RestController
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
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
    public String token(@RequestBody LoginRequest userLogin, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.login(), userLogin.password()));
        String token = tokenService.generateToken(authentication);
        UUID loggedUserId = userRepository.findByLogin(userLogin.login()).getUserId();
        Cookie jwtTokenCookie = new Cookie(loggedUserId.toString() ,token);
        jwtTokenCookie.setMaxAge(86400);
        jwtTokenCookie.setSecure(false);
        jwtTokenCookie.setHttpOnly(true);
        jwtTokenCookie.setPath("/");

        response.addCookie(jwtTokenCookie);
        return token;
    }

    /**
     * Usuwanie konta użytkownika
     *
     * @param login     login do usuwanego konta
     * @return          usuwany użytkownik
     */
    @DeleteMapping(value = "{login}/user/delete")
    public ResponseEntity<User> deleteUserAccount(@PathVariable String login) {
        User deleted = null;
        if(checkUser(login)){
            deleted = userService.deleteUserAccount(login);
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

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
        if(checkUser(login)){
            User updated = userService.updateUserAccount(login, userDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    /**
     * Wyświetlenie danych użytkownika
     *
     * @param login     login do wyświetlanego konta
     * @return          wyświetlany użytkownik
     */
    @GetMapping(value = "{login}/user")
    public ResponseEntity<User> showUserAccount(@PathVariable String login) {
        User shown = null;
        if(checkUser(login)){
             shown = userService.showUserAccount(login);
            return new ResponseEntity<>(shown, HttpStatus.OK);

        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
