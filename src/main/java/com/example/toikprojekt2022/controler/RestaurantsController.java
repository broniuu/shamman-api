package com.example.toikprojekt2022.controler;


import com.example.toikprojekt2022.dto.DishDto;
import com.example.toikprojekt2022.dto.RestaurantDto;
import com.example.toikprojekt2022.repository.RestaurantRepository;
import com.example.toikprojekt2022.service.DishService;
import com.example.toikprojekt2022.service.IRestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Klasa obsługuje endpointy związane z restauracjami
 */
@RestController
public class RestaurantsController {

    private final IRestaurantService restaurantsService;
    private final RestaurantRepository restaurantRepository;
    private final com.example.toikprojekt2022.service.DishService dishService;
    public RestaurantsController(IRestaurantService restaurantsService, RestaurantRepository restaurantRepository, DishService dishService) {

        this.restaurantsService = restaurantsService;
        this.restaurantRepository = restaurantRepository;
        this.dishService = dishService;
    }

    /**
     * Zwraca listę restauracji
     *
     * @return      lista restauracji
     */
    @GetMapping("/restaurants")

    public ResponseEntity<Iterable<RestaurantDto>> getRestaurants(){

        return new ResponseEntity(restaurantsService.GetAllRestaurants(), HttpStatus.OK);
    }

    /**
     * Wyświetla konkretną restauracje
     *
     * @param restaurantName    nazwa restauracji do wyświetlenia
     * @return                  restauracja
     */
    @GetMapping("/restaurants/{restaurantName}")
    public ResponseEntity<RestaurantDto> getRestaurantByName( @PathVariable String restaurantName){
         restaurantName= restaurantName.replaceAll("_"," ");
         RestaurantDto restaurantDto=restaurantsService.findRestaurantByName(restaurantName);
        return new ResponseEntity(restaurantDto, HttpStatus.OK);
    }

}
