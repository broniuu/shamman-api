package com.example.toikprojekt2022.model;

import lombok.Data;

import javax.persistence.*;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * Klasa reprezentuje zniżkę na produkt
 */
@Data
@Entity
public class Discount {
    @Id
    private UUID discountId;
    @Column(insertable = false, updatable = false)
    private UUID dishId;
    private double discountValue;
    private String discountCode;
    @Column(columnDefinition = "DATE")
    private LocalDate expireDate;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "dishId", nullable = false)
    private Dish dish;
    @ManyToMany
    List<User> userWithThisDiscount;
    @Transient
    private final static int discountCodeLength = 8;

    public Discount(Dish dish, double discountValue, LocalDate expireDate) {
        this.discountValue = discountValue;
        this.expireDate = expireDate;
        this.discountCode = createRandomCode(discountCodeLength);
        this.discountId = UUID.randomUUID();
        this.dish = dish;
    }
    public Discount() {
        this.discountId = UUID.randomUUID();
        this.expireDate = LocalDate.now().plusMonths(3);
        this.discountCode = createRandomCode(discountCodeLength);
    }
    private String createRandomCode(int codeLength){
        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < codeLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output ;
    }

    public List<User> getUserWithThisDiscount() {
        return userWithThisDiscount;
    }

    public void setUserWithThisDiscount(List<User> userWithThisDiscount) {
        this.userWithThisDiscount = userWithThisDiscount;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
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
