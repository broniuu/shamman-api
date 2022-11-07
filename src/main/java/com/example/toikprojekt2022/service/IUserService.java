package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.UserDto;
import com.example.toikprojekt2022.model.User;

public interface IUserService {
    User registerNewUserAccount(UserDto userDto);
}
