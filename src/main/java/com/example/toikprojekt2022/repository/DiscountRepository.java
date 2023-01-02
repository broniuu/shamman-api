package com.example.toikprojekt2022.repository;

import com.example.toikprojekt2022.model.Discount;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;
/**
 * Służy do obłśugi zniżek w bazie danych
 */
public interface DiscountRepository extends CrudRepository<Discount, UUID> {
    /**
     * znajduje zniżkę, na podane danie
     *
     * @param dishId    ID dania
     * @return          zniżka
     */
    Discount findByDishId(UUID dishId);
}
