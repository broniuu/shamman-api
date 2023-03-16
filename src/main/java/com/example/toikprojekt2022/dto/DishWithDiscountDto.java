package com.example.toikprojekt2022.dto;

import com.beust.jcommander.IStringConverter;

public class DishWithDiscountDto {
    private int dishId;
    private String name;
    private double oldPrice;
    private double newPrice;
    private String percentageDiscount;

    public void setPercentageDiscount(String percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    public double getSavedMoney() {
        return savedMoney;
    }

    public void setSavedMoney(double savedMoney) {
        this.savedMoney = savedMoney;
    }

    private double savedMoney;

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

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public String getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(double discountValue) {
        double discountConvertedToPercent = discountValue * 100;
        String discountPercentText = String.valueOf(discountConvertedToPercent);
        this.percentageDiscount = discountPercentText;
    }
}
