package com.example.toikprojekt2022.controler;

import com.example.toikprojekt2022.dto.DishDto;
import com.example.toikprojekt2022.repository.RestaurantRepository;
import com.example.toikprojekt2022.service.DishService;
import com.example.toikprojekt2022.service.IRestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
@RestController
public class DishController {
    private final IRestaurantService restaurantsService;
    private final RestaurantRepository restaurantRepository;
    private final com.example.toikprojekt2022.service.DishService dishService;

    public DishController(IRestaurantService restaurantsService, RestaurantRepository restaurantRepository, DishService dishService) {
        this.restaurantsService = restaurantsService;
        this.restaurantRepository = restaurantRepository;
        this.dishService = dishService;
    }

    @GetMapping("/restaurants/{restaurantName}/dishes")
    public ResponseEntity<Iterable<DishDto>> getDishesByRestaurantName( @PathVariable String restaurantName){
        restaurantName= restaurantName.replaceAll("_"," ");
        return new ResponseEntity(dishService.findByRestaurantId(restaurantsService.findRestaurantByName(restaurantName).getRestaurantId()), HttpStatus.OK);
    }
    @GetMapping("/restaurants/{restaurantName}/dishes/{dishName}")
    public ResponseEntity<DishDto> getDishesByRestaurantName( @PathVariable String restaurantName,@PathVariable String dishName){
        restaurantName= restaurantName.replaceAll("_"," ");
        dishName=dishName.replaceAll("_"," ");
        UUID id=restaurantsService.findRestaurantByName(restaurantName).getRestaurantId();
        return new ResponseEntity(dishService.findByDishNameAndRestaurantId(id,dishName), HttpStatus.OK);
    }
}
