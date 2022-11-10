package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.CartItemDto;
import com.example.toikprojekt2022.model.CartItem;
import com.example.toikprojekt2022.model.Dish;
import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.repository.CartItemRepository;
import com.example.toikprojekt2022.repository.DishRepository;
import com.example.toikprojekt2022.repository.UserRepository;
import org.dozer.DozerBeanMapper;
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
    public Iterable<CartItemDto> findCartItemsByOwnersLogin(String login) {
        Iterable<CartItem> cartItems = cartItemRepository.findAllByCartOwnerLogin(login);
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for(CartItem cartItem : cartItems){
            CartItemDto cartItemDto = mapper.map(cartItem, CartItemDto.class);
            cartItemDtos.add(cartItemDto);
        }
        return cartItemDtos;
    }

    @Override
    public CartItemDto upsertCartItem(String ownerLogin, UUID dishId) {
        CartItem cartItemWithTheSameDishId = getCartItemWithSameOwnersLoginAndDishId(ownerLogin, dishId);
        if(cartItemWithTheSameDishId == null){
            User owner = userRepository.findByLogin(ownerLogin);
            Dish dish = dishRepository.findById(dishId).get();
            CartItem cartItem = new CartItem(owner,dish);
            CartItemDto cartItemDto = mapper.map(cartItem, CartItemDto.class);
            cartItemRepository.save(cartItem);
            return cartItemDto;
        }
        int countOfDish = cartItemWithTheSameDishId.getCountOfDish();
        cartItemWithTheSameDishId.setCountOfDish(countOfDish + 1);
        CartItemDto cartItemDto = mapper.map(cartItemWithTheSameDishId, CartItemDto.class);
        cartItemRepository.save(cartItemWithTheSameDishId);
        return cartItemDto;
    }

    private CartItem getCartItemWithSameOwnersLoginAndDishId(String ownerLogin, UUID dishId) {
        List<CartItem> cartItems = (List<CartItem>)cartItemRepository.findAllByCartOwnerLogin(ownerLogin);
        CartItem cartItemWithTheSameDishId = cartItems.stream().filter(c -> c.getDishId().equals(dishId)).findFirst().orElse(null);
        return cartItemWithTheSameDishId;
    }

    @Override
    public CartItemDto deleteCartItem(String ownerLogin, UUID cartItemId) {
        List<CartItem> cartItems = (List<CartItem>)cartItemRepository.findAllByCartOwnerLogin(ownerLogin);
        CartItem cartItemWithTheSameId = cartItems.stream().filter(c -> c.getCartItemId().equals(cartItemId)).findFirst().orElse(null);
        if(cartItemWithTheSameId == null){
            return null;
        }
        if (cartItemWithTheSameId.getCountOfDish() > 1) {
            int countOfDish = cartItemWithTheSameId.getCountOfDish();
            cartItemWithTheSameId.setCountOfDish(countOfDish - 1);
            cartItemRepository.save(cartItemWithTheSameId);

        } else {
            cartItemRepository.deleteById(cartItemWithTheSameId.getCartItemId());
        }
        CartItemDto cartItemDto = mapper.map(cartItemWithTheSameId, CartItemDto.class);
        return cartItemDto;
    }
}
