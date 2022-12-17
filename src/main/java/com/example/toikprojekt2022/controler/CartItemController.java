package com.example.toikprojekt2022.controler;

import com.example.toikprojekt2022.dto.CartItemDto;
import com.example.toikprojekt2022.repository.CartItemRepository;
import com.example.toikprojekt2022.service.ICartItemService;
import com.example.toikprojekt2022.service.UserService;
import com.google.zxing.WriterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static com.example.toikprojekt2022.ReceiptPrinter.PdfPrinter.makePdf;

@RestController
public class CartItemController {

    private final ICartItemService cartItemService;
    private final UserService userService;
    public CartItemController(ICartItemService cartItemService, CartItemRepository cartItemRepository,UserService userService) {
        this.cartItemService = cartItemService;
        this.userService=userService;
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
    @GetMapping ("{login}/usercart/checkout")
    public void checkout(@PathVariable String login) throws IOException, WriterException {
        Iterable<CartItemDto> ci=cartItemService.findCartItemsByOwnersLogin(login);
        List<CartItemDto> cartitems= StreamSupport.stream(ci.spliterator(), false).toList();
        makePdf(userService.showUserAccount(login),cartitems, true,"test");
        cartitems.forEach((cartItem) ->cartItemService.deleteCartItem(login,cartItem.getCartItemId()));

    }

}
