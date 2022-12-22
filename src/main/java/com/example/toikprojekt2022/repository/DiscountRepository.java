package com.example.toikprojekt2022.repository;

import com.example.toikprojekt2022.model.Discount;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DiscountRepository extends CrudRepository<Discount, UUID> {
    Discount findByDishId(UUID dishId);
}
