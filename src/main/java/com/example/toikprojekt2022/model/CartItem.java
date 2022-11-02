package com.example.toikprojekt2022.model;

import javax.persistence.*;
import java.util.UUID;

/**
 * Klasa reprezentuje produkt z koszyka
 */

@Entity
public class CartItem {
    @Id
    private UUID cartItemId;
    @Column(insertable = false, updatable = false)
    private UUID cartOwnerId;
    @Column(insertable = false, updatable = false)
    private int dishId;
    private int countOfDish;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cartOwnerId", nullable = false)
    private User cartOwner;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dishId", nullable = false)
    private Dish dish;
    public CartItem(User cartOwner, Dish dish) {
        this.cartItemId = UUID.randomUUID();
        this.cartOwner = cartOwner;
        this.dish = dish;
        this.countOfDish = 1;
    }
    public CartItem(User cartOwner, Dish dish,int countOfDish) {
        this.cartItemId = UUID.randomUUID();
        this.cartOwner = cartOwner;
        this.dish = dish;
        this.countOfDish = countOfDish;
    }
    public CartItem(UUID cartItemId, User cartOwner, Dish dish, int countOfDish) {
        this.cartItemId = cartItemId;
        this.cartOwner = cartOwner;
        this.dish = dish;
        this.countOfDish = countOfDish;
    }

    public CartItem() {

    }

    public UUID getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(UUID cartItemId) {
        this.cartItemId = cartItemId;
    }

    public User getCartOwner() {
        return cartOwner;
    }

    public void setCartOwner(User cartOwner) {
        this.cartOwner = cartOwner;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getCountOfDish() {
        return countOfDish;
    }

    public void setCountOfDish(int countOfDish) {
        this.countOfDish = countOfDish;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public UUID getCartOwnerId() {
        return cartOwnerId;
    }

    public void setCartOwnerId(UUID cartOwnerId) {
        this.cartOwnerId = cartOwnerId;
    }
}
