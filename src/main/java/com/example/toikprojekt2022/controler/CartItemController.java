package com.example.toikprojekt2022.controler;

import com.example.toikprojekt2022.dto.CartItemDto;
import com.example.toikprojekt2022.repository.CartItemRepository;
import com.example.toikprojekt2022.service.ICartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class CartItemController {

    private final ICartItemService cartItemService;

    public CartItemController(ICartItemService cartItemService, CartItemRepository cartItemRepository) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("{login}/usercart")
    public ResponseEntity<Iterable<CartItemDto>> getCartItemsByUser(@PathVariable String login){
        return new ResponseEntity(cartItemService.findCartItemsByOwnersLogin(login), HttpStatus.OK);
    }

    @GetMapping("{login}/usercart/{cartItemId}")
    public ResponseEntity<CartItemDto> getUserCartItemByUserAndCartItemId(@PathVariable String login, @PathVariable UUID cartItemId){
        return new ResponseEntity(cartItemService.findUserCartItemById(login,cartItemId), HttpStatus.OK);
    }

    @PostMapping("{login}/usercart/{dishId}/save/{count}")
    public ResponseEntity<CartItemDto> upsertCartItem(@PathVariable String login, @PathVariable UUID dishId, @PathVariable int count){
        CartItemDto cartItemDto = cartItemService.upsertCartItem(login,dishId,count);
        return new ResponseEntity(cartItemDto, HttpStatus.OK);
    }
    @DeleteMapping ("{login}/usercart/{cartItemId}/delete")
    public ResponseEntity<CartItemDto> deleteCartItem(@PathVariable String login, @PathVariable UUID cartItemId){
        CartItemDto cartItemDto = cartItemService.deleteCartItem(login, cartItemId);
        return new ResponseEntity(cartItemDto, HttpStatus.OK);
    }
}
