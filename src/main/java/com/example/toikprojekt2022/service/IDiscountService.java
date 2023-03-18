package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.DiscountDto;
import com.example.toikprojekt2022.dto.DiscountToViewDto;
import com.example.toikprojekt2022.dto.DishWithDiscountDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Interfejs do obsługi klasy CartItem
 */
public interface IDiscountService {
    Page<DiscountToViewDto> findDiscountDtos(int pageNumber);
    DishWithDiscountDto tryUnlockDiscount(String discountCode, String login);
    Iterable<DiscountDto> getDiscountsOfUser(String userLogin);
}
