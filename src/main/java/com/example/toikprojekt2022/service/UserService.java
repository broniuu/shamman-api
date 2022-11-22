package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.UserDto;
import com.example.toikprojekt2022.exception.UserAlreadyExistException;
import com.example.toikprojekt2022.exception.UserNotFoundException;
import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.repository.UserRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        User userToDelete = userRepository.findByLogin(login);
        if (userToDelete == null) {
            throw new UserNotFoundException("There is no user with that login: "
                    + login);
        }
        userRepository.delete(userToDelete);
        return userToDelete;
    }

    @Override
    public User updateUserAccount(String login, UserDto userDto) {
        User userToUpdate = userRepository.findByLogin(login);
        if (userToUpdate == null) {
            throw new UserNotFoundException("There is no user with that login: "
                    + login);
        }
        changeUserDtoToUser(userDto, userToUpdate);
        userRepository.save(userToUpdate);
        return userToUpdate;
    }

    private void changeUserDtoToUser(UserDto userDto, User userToUpdate) {
        userToUpdate.setLogin(userDto.getLogin());
        encodePassword(userToUpdate, userDto);
        userToUpdate.setName(userDto.getName());
        userToUpdate.setSurname(userDto.getSurname());
        userToUpdate.setAddress(userDto.getAddress());
        userToUpdate.setDebitCardNumber(userDto.getDebitCardNumber());
        userToUpdate.setExpireDate(userDto.getExpireDate());
        userToUpdate.setCvv(userDto.getCvv());
        userToUpdate.setEmail(userDto.getEmail());
    }

    @Override
    public User showUserAccount(String login) {
        if (userRepository.findByLogin(login) == null) {
            throw new UserNotFoundException("There is no user with that login: "
                    + login);
        }
        return userRepository.findByLogin(login);
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
