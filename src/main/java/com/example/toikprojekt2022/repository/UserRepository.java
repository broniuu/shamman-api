package com.example.toikprojekt2022.repository;

import com.example.toikprojekt2022.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.login = ?1")
    User findByLogin(String login);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
    @Query("SELECT u.userId FROM User u WHERE u.login = ?1")
    UUID findUserIdByLogin(String login);
}
