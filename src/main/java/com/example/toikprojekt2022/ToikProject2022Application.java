package com.example.toikprojekt2022;

import com.example.toikprojekt2022.model.CartItem;
import com.example.toikprojekt2022.model.Dish;
import com.example.toikprojekt2022.model.Restaurant;
import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.repository.CartItemRepository;
import com.example.toikprojekt2022.repository.DishRepository;
import com.example.toikprojekt2022.repository.RestaurantRepository;
import com.example.toikprojekt2022.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ToikProject2022Application implements CommandLineRunner {

	private UserRepository userRepository;
	private RestaurantRepository restaurantRepository;
	private CartItemRepository cartItemRepository;
	private DishRepository dishRepository;
	public ToikProject2022Application(UserRepository userRepository, RestaurantRepository restaurantRepository, CartItemRepository cartItemRepository, DishRepository dishRepository) {
		this.userRepository = userRepository;
		this.restaurantRepository = restaurantRepository;
		this.cartItemRepository = cartItemRepository;
		this.dishRepository = dishRepository;
	}
	public static void main(String[] args) {
		SpringApplication.run(ToikProject2022Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Restaurant restaurant = new Restaurant("restauracja", "url");
		restaurantRepository.save(restaurant);
		Dish dish = new Dish(
				"STEK Z POLĘDWICY WOŁOWEJ",
				"warzywami pieczonymi w miodzie, z sosem pieprzowym",
				75.0,
				"https://static.gotujmy.pl/ZDJECIE_PRZEPISU_ETAP/steak-z-poledwicy-wolowej-526206.jpg",
				restaurant);
		dishRepository.save(dish);
		User user = new User(
				"login01",
				"Password0@",
				"Kamil",
				"Martyka",
				"Tatnow,kolowa,512i,33-120",
				"4238087491661008",
				"12/23",
				"324",
				"mlodywrz3@amorki.pl"
		);
		userRepository.save(user);
		cartItemRepository.save(new CartItem(
				user,
				dish
		));

		Iterable<User> users = userRepository.findAll();
		Iterable<Restaurant> restaurants = restaurantRepository.findAll();

		Iterable<Dish> generatedDishes = List.of(
				new Dish(
						"STEK Z POLĘDWICY WOŁOWEJ",
						"warzywami pieczonymi w miodzie, z sosem pieprzowym",
						75.0,
						"https://static.gotujmy.pl/ZDJECIE_PRZEPISU_ETAP/steak-z-poledwicy-wolowej-526206.jpg",
						restaurant
				)
		);
	}

}
