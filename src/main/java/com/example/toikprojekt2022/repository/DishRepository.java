package com.example.toikprojekt2022.repository;

import com.example.toikprojekt2022.model.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DishRepository extends CrudRepository<Dish, UUID> {
}
