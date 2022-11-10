package com.example.toikprojekt2022.dto;

import com.example.toikprojekt2022.model.Dish;
import com.example.toikprojekt2022.model.User;

import javax.persistence.*;
import java.util.UUID;

public class CartItemDto {
    private UUID cartItemId;
    private int countOfDish;
    private User cartOwner;
    private DishDto dish;

    public UUID getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(UUID cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCountOfDish() {
        return countOfDish;
    }

    public void setCountOfDish(int countOfDish) {
        this.countOfDish = countOfDish;
    }

    public User getCartOwner() {
        return cartOwner;
    }

    public void setCartOwner(User cartOwner) {
        this.cartOwner = cartOwner;
    }

    public DishDto getDish() {
        return dish;
    }

    public void setDish(DishDto dish) {
        this.dish = dish;
    }
}
