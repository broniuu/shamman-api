package com.example.toikprojekt2022.controler;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantsController {

//    public RestaurantsController(RestaurantRepository restaurantRepository) {
//        this.restaurantRepositor = new RestaurantServicerestaurantRepository);
//    }
    @GetMapping(value = "/restaurants")
    public String viewRestaurants (){
        return "test";
    }
}
