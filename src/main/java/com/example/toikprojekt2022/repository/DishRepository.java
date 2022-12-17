package com.example.toikprojekt2022.repository;

import com.example.toikprojekt2022.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DishRepository extends PagingAndSortingRepository<Dish, UUID> {
    @Query("SELECT d FROM Dish d WHERE d.restaurantId = ?1 and d.name=?2")
    Optional<Dish>findByDishNameAndRestaurantId(UUID id, String name );
    @Query("SELECT d FROM Dish d WHERE d.restaurantId = ?1")
    Iterable<Dish> findByRestaurantId(UUID id);
    @Query(value = "SELECT d FROM Dish d WHERE d.restaurantId = ?1")
    Page<Dish> findDishesByRestaurantName(UUID restaurantId, Pageable pageable);
    int countDishesByRestaurantId(UUID restaurantId);

}
