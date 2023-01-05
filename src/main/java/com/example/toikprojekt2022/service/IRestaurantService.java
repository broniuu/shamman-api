package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.RestaurantDto;

/**
 * Interfejs do obsługi klasy Restaurant
 */
public interface IRestaurantService {
    RestaurantDto findRestaurantByName(String name);
    Iterable<RestaurantDto> GetAllRestaurants();

}
