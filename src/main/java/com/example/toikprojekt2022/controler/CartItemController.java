package com.example.toikprojekt2022.controler;

import com.example.toikprojekt2022.dto.CartItemDto;
import com.example.toikprojekt2022.model.CartItem;
import com.example.toikprojekt2022.repository.CartItemRepository;
import com.example.toikprojekt2022.service.ICartItemService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class CartItemController {

    private final ICartItemService cartItemService;

    public CartItemController(ICartItemService cartItemService, CartItemRepository cartItemRepository) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("{login}/usercart")
    public Iterable<CartItemDto> getCartItemsByUser(@PathVariable String login){
        return cartItemService.findCartItemsByOwnersLogin(login);
    }

    @PostMapping("{login}/usercart/{dishId}/save")
    public CartItemDto getCartItemsByUser(@PathVariable String login, @PathVariable UUID dishId){
        CartItemDto cartItemDto = cartItemService.upsertCartItem(login,dishId);
        return cartItemDto;
    }
    @DeleteMapping ("{login}/usercart/{cartItemId}/delete")
    public CartItemDto etCartItemsByUser(@PathVariable String login, @PathVariable UUID cartItemId){
        CartItemDto cartItemDto = cartItemService.deleteCartItem(login, cartItemId);
        return cartItemDto;
    }
}
