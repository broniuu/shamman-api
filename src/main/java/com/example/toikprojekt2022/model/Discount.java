package com.example.toikprojekt2022.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;
@Data
@Entity
public class Discount {
    @Id
    private UUID discountId;
    @Column(insertable = false, updatable = false)
    private UUID dishId;
    private double discountValue;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "dishId", nullable = false)
    private Dish dish;


    public Discount(Dish dish, double discountValue) {
        this.discountValue = discountValue;
        this.discountId = UUID.randomUUID();
        this.dish = dish;
    }

    public Discount() {
        this.discountId = UUID.randomUUID();
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public UUID getDiscountId() {
        return discountId;
    }

    public void setDiscountId(UUID discountId) {
        this.discountId = discountId;
    }

    public UUID getDishId() {
        return dishId;
    }

    public void setDishId(UUID dishId) {
        this.dishId = dishId;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
