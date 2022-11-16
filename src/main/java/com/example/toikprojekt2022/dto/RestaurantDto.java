package com.example.toikprojekt2022.dto;

import com.example.toikprojekt2022.model.Dish;

import java.util.List;
import java.util.UUID;

public class RestaurantDto {
    private UUID restaurantId;
    private String name;
    private String imageUrl;

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
