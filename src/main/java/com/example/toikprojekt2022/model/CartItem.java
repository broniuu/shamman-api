package com.example.toikprojekt2022.model;

import javax.persistence.*;

/**
 * Klasa reprezentuje produkt z koszyka
 */

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ_ZONE")
    private int cartItemId;
    private int countOfDish;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User cartOwner;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dishId", nullable = false)
    private Dish dish;

    public CartItem(User cartOwner, Dish dish) {
        this.cartOwner = cartOwner;
        this.dish = dish;
        this.countOfDish = 1;
    }
    public CartItem(User cartOwner, Dish dish,int countOfDish) {
        this.cartOwner = cartOwner;
        this.dish = dish;
        this.countOfDish = countOfDish;
    }
    public CartItem(int cartItemId, User cartOwner, Dish dish, int countOfDish) {
        this.cartItemId = cartItemId;
        this.cartOwner = cartOwner;
        this.dish = dish;
        this.countOfDish = countOfDish;
    }

    public CartItem() {

    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
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
}
