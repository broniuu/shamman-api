package com.example.toikprojekt2022.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Klasa reprezentuje restaurację
 */

@Entity
public class Restaurant {
    @Id
    private UUID restaurantId;
    private String name;
    private String imageUrl;
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Dish> dishes;

    public Restaurant(String name, String imageUrl) {
        this.restaurantId = UUID.randomUUID();
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Restaurant(UUID restaurantId, String name, String imageUrl) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Restaurant() {
        this.restaurantId = UUID.randomUUID();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

}
