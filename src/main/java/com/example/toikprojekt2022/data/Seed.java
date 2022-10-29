package com.example.toikprojekt2022.data;

import com.example.toikprojekt2022.model.CartItem;
import com.example.toikprojekt2022.model.Dish;
import com.example.toikprojekt2022.model.Restaurant;
import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.repository.CartItemRepository;
import com.example.toikprojekt2022.repository.DishRepository;
import com.example.toikprojekt2022.repository.RestaurantRepository;
import com.example.toikprojekt2022.repository.UserRepository;

import java.util.List;

public class Seed {

    public Seed(UserRepository userRepository,
                RestaurantRepository restaurantRepository,
                CartItemRepository cartItemRepository,
                DishRepository dishRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.cartItemRepository = cartItemRepository;
        this.dishRepository = dishRepository;
    }

    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;
    private CartItemRepository cartItemRepository;
    private DishRepository dishRepository;

    public void seedData() {
        List<Restaurant> restaurants = List.of(
                new Restaurant("Restauracja u Jana" ,""),
                new Restaurant("American Dream" ,""),
                new Restaurant("Bistro Przepis" ,""),
                new Restaurant("Domowa Bistro" ,""),
                new Restaurant("Restauracja Pyza" ,""),
                new Restaurant("Restauracja Soprano" ,""),
                new Restaurant("Restauracja Różana" ,""),
                new Restaurant("Restauracja Pół na Pół" ,"")
        );
        restaurantRepository.saveAll(restaurants);
        Restaurant firstRestaurant = restaurants.get(1);
        Iterable<Dish> dishes = List.of(
                new Dish(
                        "STEK Z POLĘDWICY WOŁOWEJ",
                        "warzywami pieczonymi w miodzie, z sosem pieprzowym",
                        75.0,
                        "https://static.gotujmy.pl/ZDJECIE_PRZEPISU_ETAP/steak-z-poledwicy-wolowej-526206.jpg",
                        firstRestaurant
                ),
                new Dish(
                        "KOTLET DE VOLAILLE",
                        "Kotlet de Volaille z frytkami i surówką",
                        28.00,
                        "https://bi.im-g.pl/im/99/c5/18/z25975449AMP,Kotlet-de-volaille-z-maslem-i-czosnkiem---klasyk-n.jpg",
                        firstRestaurant
                )
        );
        dishRepository.saveAll(dishes);

    }
}
