package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.UserDto;
import com.example.toikprojekt2022.exception.UserAlreadyExistException;
import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.repository.UserRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    @Override
    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + userDto.getEmail());
        }
        if (loginExists(userDto.getLogin())) {
            throw new UserAlreadyExistException("There is an account with that login: "
                    + userDto.getLogin());
        }
        User userToRegister = mapper.map(userDto,User.class);
        return userRepository.save(userToRegister);
    }
    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean loginExists(String login) {
        return userRepository.findByLogin(login) != null;
    }
}
