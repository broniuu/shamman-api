package com.example.toikprojekt2022.repository;

import com.example.toikprojekt2022.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.login = ?1")
    User findByLogin(String login);
}
