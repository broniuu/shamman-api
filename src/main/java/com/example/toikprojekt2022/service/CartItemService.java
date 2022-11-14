package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.CartItemDto;
import com.example.toikprojekt2022.exception.ResourceNotFoundException;
import com.example.toikprojekt2022.model.CartItem;
import com.example.toikprojekt2022.model.Dish;
import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.repository.CartItemRepository;
import com.example.toikprojekt2022.repository.DishRepository;
import com.example.toikprojekt2022.repository.UserRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    public CartItemService(CartItemRepository cartItemRepository, DishRepository dishRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    @Override
    public Iterable<CartItemDto> findCartItemsByOwnersLogin(String login) throws ResourceNotFoundException {
        List<CartItem> cartItems = (List<CartItem>) cartItemRepository.findAllByCartOwnerLogin(login);
        if (cartItems.isEmpty()) throw new ResourceNotFoundException("Not found CartItems of user " + login);
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for(CartItem cartItem : cartItems){
            CartItemDto cartItemDto = mapper.map(cartItem, CartItemDto.class);
            cartItemDtos.add(cartItemDto);
        }
        return cartItemDtos;
    }

    @Override
    public CartItemDto upsertCartItem(String ownerLogin, UUID dishId, int countOfDish) {
        CartItem cartItemWithTheSameDishId = cartItemRepository.findByOwnerLoginAndDishId(ownerLogin, dishId).orElse(null);
        if (cartItemWithTheSameDishId == null) {
            User owner = userRepository.findByLogin(ownerLogin);
            Dish dish = dishRepository.findById(dishId).get();
            CartItem cartItem = new CartItem(owner, dish, countOfDish);
            CartItemDto cartItemDto = mapper.map(cartItem, CartItemDto.class);
            cartItemRepository.save(cartItem);
            return cartItemDto;
        }
        cartItemWithTheSameDishId.setCountOfDish(countOfDish);
        CartItemDto cartItemDto = mapper.map(cartItemWithTheSameDishId, CartItemDto.class);
        cartItemRepository.save(cartItemWithTheSameDishId);
        return cartItemDto;
    }

    @Override
    public CartItemDto deleteCartItem(String ownerLogin, UUID cartItemId) throws ResourceNotFoundException {
        CartItem cartItemWithTheSameId = cartItemRepository.findByOwnerLoginAndCartItemId(
                ownerLogin,
                cartItemId).orElseThrow(
                        () -> new ResourceNotFoundException("Not found CartItem of user " + ownerLogin + " with id = " + cartItemId)
        );
        cartItemRepository.deleteByCartItemId(cartItemWithTheSameId.getCartItemId());
        CartItemDto cartItemDto = mapper.map(cartItemWithTheSameId, CartItemDto.class);
        return cartItemDto;
    }

    @Override
    public CartItemDto findUserCartItemById(String ownerLogin, UUID cartItemId) throws ResourceNotFoundException {
        CartItem cartItem = cartItemRepository.findByOwnerLoginAndCartItemId(
                ownerLogin,
                cartItemId).orElseThrow(
                        () -> new ResourceNotFoundException("Not found CartItem of user " + ownerLogin + " with id = " + cartItemId)
        );
        CartItemDto cartItemDto = mapper.map(cartItem, CartItemDto.class);
        return cartItemDto;
    }
}