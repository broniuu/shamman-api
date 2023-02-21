package com.example.toikprojekt2022.dto;
import lombok.Data;

@Data
public class DiscountDto {
    private String dishName;
    private String discountAsPercentages;

    public DiscountDto(String dishName, String discountAsPercentages) {
        this.dishName = dishName;
        this.discountAsPercentages = discountAsPercentages;
    }
}
