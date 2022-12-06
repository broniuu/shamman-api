package com.example.toikprojekt2022.extension;

import com.example.toikprojekt2022.dto.DiscountDto;
import com.example.toikprojekt2022.model.Discount;

public class DiscountExtension {
    public static DiscountDto toDiscountDto(Discount discount){
        String discountAsString = String.valueOf(discount.getDiscountValue() * 100);
        String discountAsPercentages = discountAsString.replaceAll("\\.0","") + " %";
        discount.getDish().getRestaurant();
        return new DiscountDto(
                discount.getDish().getName(),
                discountAsPercentages);
    }
}
