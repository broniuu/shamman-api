package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.UserDto;
import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.repository.UserRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    public void registerUser(UserDto registerDto) throws RuntimeException{
        List<UserDto> userDtos = getUserDtos();
        if (userDtos.contains(registerDto)){
            throw new RuntimeException("user is arleady in databse!");
        }
        User userToRegister = mapper.map(registerDto,User.class);
        userRepository.save(userToRegister);
    }

    private List<UserDto> getUserDtos() {
        Iterable<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user:users) {
            UserDto userDto = mapper.map(user,UserDto.class);
            userDtos.add(userDto);
        }
        return userDtos;
    }

}
