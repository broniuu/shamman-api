package com.example.toikprojekt2022.controler;

import com.example.toikprojekt2022.dto.CartItemDto;
import com.example.toikprojekt2022.exception.ResourceNotFoundException;
import com.example.toikprojekt2022.repository.CartItemRepository;
import com.example.toikprojekt2022.service.ICartItemService;
import org.springframework.data.domain.Page;
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

/**
 * Klasa obsługuje endpointy związane z koszykiem użytkownika
 */
@RestController
public class CartItemController {

    private final ICartItemService cartItemService;
    private final UserService userService;
    public CartItemController(ICartItemService cartItemService,UserService userService) {
        this.cartItemService = cartItemService;
        this.userService=userService;
    }

    /**
     * Wyświetla dania z koszyka danego użytkownika
     *
     * @param login         login właściciela koszyka
     * @param pageNumber    numer Strony, którą chcemy wyświetlić
     * @return              dania z koszyka
     */
    @GetMapping(value = "{login}/usercart", params = {"p"})
    public ResponseEntity<Iterable<CartItemDto>> getCartItemsByUser(
            @PathVariable String login,
            @RequestParam("p") int pageNumber){
        Page<CartItemDto> resultPage = cartItemService.findPaginatedCartItemsByOwnersLogin(login, pageNumber);
        return new ResponseEntity(resultPage, HttpStatus.OK);
    }

    /**
     * Wyświetla konkretne danie z koszyka danego użytkownika
     *
     * @param login         login właściciela koszyka
     * @param cartItemId    ID dania z koszyka
     * @return              danie z koszyka
     */
    @GetMapping("{login}/usercart/{cartItemId}")
    public ResponseEntity<CartItemDto> getUserCartItemByUserAndCartItemId(@PathVariable String login, @PathVariable UUID cartItemId){
        return new ResponseEntity(cartItemService.findUserCartItemById(login,cartItemId), HttpStatus.OK);
    }

    /**
     * Dodaje danie do koszyka
     *
     * @param login     login właściciela koszyka
     * @param dishId    ID dania
     * @param count     ilość sztuk dania do zapisania w koszyku
     * @return          nowe danie z koszyka
     */
    @PostMapping("{login}/usercart/{dishId}/save/{count}")
    public ResponseEntity<CartItemDto> upsertCartItem(@PathVariable String login, @PathVariable UUID dishId, @PathVariable int count){
        CartItemDto cartItemDto = cartItemService.upsertCartItem(login,dishId,count);
        return new ResponseEntity(cartItemDto, HttpStatus.OK);
    }

    /**
     * Usówa wszystkie sztuki dania z koszyka
     *
     * @param login         login właściciela koszyka
     * @param cartItemId    ID dania z koszyka
     * @return              usunięte danie z koszyka
     */
    @DeleteMapping ("{login}/usercart/{cartItemId}/delete")
    public ResponseEntity<CartItemDto> deleteCartItem(@PathVariable String login, @PathVariable UUID cartItemId){
        CartItemDto cartItemDto = cartItemService.deleteCartItem(login, cartItemId);
        return new ResponseEntity(cartItemDto, HttpStatus.OK);
    }

    /**
     * Dokonuje transackji, czyści koszyk i wysyła paragon na maila
     *
     * @param login             login właściciela koszyka
     * @return                  komunikat o udanej transakcji
     * @throws IOException
     * @throws WriterException
     */
    @GetMapping ("{login}/usercart/checkout")
    public ResponseEntity<String> checkout(@PathVariable String login) throws IOException, WriterException {
        Iterable<CartItemDto> ci = cartItemService.findCartItemsWithDiscountPriceByOwnersLogin(login);
        if (ci == null)
            throw new ResourceNotFoundException("The usercart is empty!");
        List<CartItemDto> cartitems = StreamSupport.stream(ci.spliterator(), false).toList();
        makePdf(userService.showUserAccount(login),cartitems, true,"test");
        cartitems.forEach((cartItem) ->cartItemService.deleteCartItem(login,cartItem.getCartItemId()));
        return new ResponseEntity<>("Transaction ended succesfuly!", HttpStatus.OK);
    }

}
