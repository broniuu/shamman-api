package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.DiscountDto;
import com.example.toikprojekt2022.dto.DiscountToViewDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Interfejs do obsługi klasy CartItem
 */
public interface IDiscountService {
    Page<DiscountToViewDto> findDiscountDtos(int pageNumber);
    boolean tryUnlockDiscount(UUID discountId, String discountCode, String login);
    Iterable<DiscountDto> getDiscountsOfUser(String userLogin);
}
