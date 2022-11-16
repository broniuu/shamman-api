package com.example.toikprojekt2022.controler;


import com.example.toikprojekt2022.dto.RestaurantDto;
import com.example.toikprojekt2022.repository.RestaurantRepository;
import com.example.toikprojekt2022.service.IRestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantsController {

    private final IRestaurantService restaurantsService;
    private final RestaurantRepository restaurantRepository;
    public RestaurantsController(IRestaurantService restaurantsService, RestaurantRepository restaurantRepository) {
        this.restaurantsService = restaurantsService;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("{login}/Restaurants")
    public ResponseEntity<Iterable<RestaurantDto>> getCartItemsByUser(@PathVariable String login){
        return new ResponseEntity(restaurantsService.GetAllRestaurants(), HttpStatus.OK);
    }

    @GetMapping("{login}/Restaurants/{restaurantName}")
    public ResponseEntity<RestaurantDto> getUserCartItemByUserAndCartItemId(@PathVariable String login, @PathVariable String restaurantName){
         restaurantName= restaurantName.replaceAll("_"," ");
         System.out.println(restaurantName);
        return new ResponseEntity(restaurantsService.findRestaurantByName(restaurantName), HttpStatus.OK);
    }


}
