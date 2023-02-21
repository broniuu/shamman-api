package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.RestaurantDto;
import com.example.toikprojekt2022.exception.DishNotFoundException;
import com.example.toikprojekt2022.exception.RestarurantNotFoundException;
import com.example.toikprojekt2022.model.CartItem;
import com.example.toikprojekt2022.model.Restaurant;
import com.example.toikprojekt2022.repository.RestaurantRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Obsługuje operacje związane z zarządzaniem restauracjami
 */
@Service
public class RestaurantService implements IRestaurantService{
    private final RestaurantRepository restaurantRepository;
    private final Mapper mapper;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    /**
     * Znajduje restauracje po nazwie
     *
     * @param name      nazwa restauracji
     * @return          restauracja
     */
    @Override
    public RestaurantDto findRestaurantByName(String name) {
        Restaurant restaurant= restaurantRepository.findByName(name).orElse(null);
        if(restaurant == null) throw new RestarurantNotFoundException("Did not found any Restaurants with name:"+name);
        RestaurantDto restaurantDto = new RestaurantDto();
        mapper.map(restaurant,restaurantDto);
        return restaurantDto;
    }

    /**
     * Zwraca wszystkie restauracje
     *
     * @return      restauracje
     */
    @Override
    public Iterable<RestaurantDto> GetAllRestaurants() {
        List<Restaurant> restaurants = (List<Restaurant>) restaurantRepository.findAll();
        if (restaurants.isEmpty()) throw new RestarurantNotFoundException("Did not found any Restaurants");
        List<RestaurantDto> restaurantDtos = new ArrayList<>();
        for(Restaurant restaurant : restaurants){
            RestaurantDto restaurantDto = mapper.map(restaurant, RestaurantDto.class);
            restaurantDtos.add(restaurantDto);
        }
        return restaurantDtos;
    }
}
