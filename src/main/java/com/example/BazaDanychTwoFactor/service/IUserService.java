package com.example.BazaDanychTwoFactor.service;

import com.example.BazaDanychTwoFactor.dto.UserDto;
import com.example.BazaDanychTwoFactor.model.User;

/**
 * Interfejs do obsługi klasy User
 */
public interface IUserService {
    User registerNewUserAccount(UserDto userDto);
    User deleteUserAccount(String login);
    User updateUserAccount(String login, UserDto userDto);
    User showUserAccount(String login);
}
