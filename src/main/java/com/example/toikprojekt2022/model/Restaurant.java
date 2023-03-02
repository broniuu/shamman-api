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
    private String backgroundImageUrl;
    private int score;
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Dish> dishes;

    public Restaurant(String name, String imageUrl, String backgroundImageUrl, int score) {
        this.backgroundImageUrl = backgroundImageUrl;
        this.score = score;
        this.restaurantId = UUID.randomUUID();
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Restaurant() {
        this.restaurantId = UUID.randomUUID();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
