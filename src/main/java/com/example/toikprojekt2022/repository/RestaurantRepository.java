package com.example.toikprojekt2022.repository;

import com.example.toikprojekt2022.dto.RestaurantDto;
import com.example.toikprojekt2022.model.Restaurant;
import com.example.toikprojekt2022.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, UUID> {
    @Query("SELECT r FROM Restaurant r WHERE r.name = ?1")
    Optional<Restaurant> findByName(String name);
}
