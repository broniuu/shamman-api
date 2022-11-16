package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.UserDto;
import com.example.toikprojekt2022.exception.UserAlreadyExistException;
import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.repository.UserRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final Mapper mapper;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        userToRegister.setLogin(userDto.getLogin().toLowerCase());
        encodePassword(userToRegister, userDto);
        return userRepository.save(userToRegister);
    }

    @Override
    public User deleteUserAccount(String login) {
        User user = userRepository.findByLogin(login);
        userRepository.delete(user);
        return user;
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean loginExists(String login) {
        return userRepository.findByLogin(login) != null;
    }
    private void encodePassword( User user, UserDto userDto){
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    }
}
