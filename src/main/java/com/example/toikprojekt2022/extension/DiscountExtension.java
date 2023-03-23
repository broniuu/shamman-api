package com.example.toikprojekt2022.extension;

import com.example.toikprojekt2022.dto.DiscountToViewDto;
import com.example.toikprojekt2022.model.Discount;

/**
 * klasa zawiera metody rozszerzające te dostępne dla klasy Discount
 */
public class DiscountExtension {
    /**
     * Konwertuje object Discount do obietku DiscountDto
     *
     * @param discount  obiekt do konwersji
     * @return          skonwertowany obiekt Discount Dto
     */
    public static DiscountToViewDto toDiscountDto(Discount discount){
        String discountAsString = String.valueOf(discount.getDiscountValue() * 100);
        String discountAsPercentages = discountAsString.replaceAll("\\.0","") + " %";
        discount.getDish().getRestaurant();
        return new DiscountToViewDto(
                discount.getDish().getName(),
                discountAsPercentages);
    }
}
