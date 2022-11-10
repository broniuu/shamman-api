package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.CartItemDto;

import java.util.UUID;

public interface ICartItemService {
    Iterable<CartItemDto> findCartItemsByOwnersLogin(String login);
    CartItemDto upsertCartItem(String ownerLogin, UUID dishId);
    CartItemDto deleteCartItem(String ownerLogin, UUID cartItemId);
}
