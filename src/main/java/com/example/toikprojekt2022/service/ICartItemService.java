package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.CartItemDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Interfejs do obsługi klasy CartItem
 */
public interface ICartItemService {
    Iterable<CartItemDto> findCartItemsWithDiscountPriceByOwnersLogin(String login);
    CartItemDto upsertCartItem(String ownerLogin, UUID dishId, int countOfDish);
    CartItemDto deleteCartItem(String ownerLogin, UUID cartItemId);
    CartItemDto findUserCartItemById(String ownerLogin, UUID cartItemId);
    Page<CartItemDto> findPaginatedCartItemsByOwnersLogin(String login, int pageNumber);

}
