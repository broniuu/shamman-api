package com.example.toikprojekt2022.model;

import javax.persistence.*;

/**
 * Klasa reprezentuje danie
 */

@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ_ZONE")
    private int dishId;
    private String name;
    private String description;
    private Double price;
    @Column(insertable = false, updatable = false)
    private int restaurantId;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "restaurantId", nullable = false)
    private Restaurant restaurant;

    public Dish(int dishId, String name, String description, Double price, String imageUrl, Restaurant restaurant) {
        this.dishId = dishId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.restaurant = restaurant;
    }

    public Dish() {

    }

    @Override
    public String toString() {
        return "Dish{" +
                "DishId=" + dishId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", restaurantId=" + restaurantId +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

}
