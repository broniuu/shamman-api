package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.DishDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IDishService {

    DishDto findByDishNameAndRestaurantId( UUID id,String name);
    Iterable<DishDto> findByRestaurantId(UUID id);
    Page<DishDto> findDishesByRestaurantName(String restaurantName, int pageNumber);
}
