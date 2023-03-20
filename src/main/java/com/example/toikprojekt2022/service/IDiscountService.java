package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.CartItemDto;
import com.example.toikprojekt2022.dto.DiscountDto;
import com.example.toikprojekt2022.dto.DiscountToViewDto;
import com.example.toikprojekt2022.dto.DishWithDiscountDto;
import com.example.toikprojekt2022.model.Discount;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Interfejs do obsługi klasy CartItem
 */
public interface IDiscountService {
    Page<DiscountToViewDto> findDiscountDtos(int pageNumber);
    DishWithDiscountDto tryUnlockDiscount(String discountCode, String login);
    Iterable<DiscountDto> getDiscountsOfUser(String userLogin);
    Discount saveUsedDiscountWithItsOwner(String discountCode, String ownersLogin, List<CartItemDto> cartItemDtos);
}
