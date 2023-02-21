package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.model.Discount;
import com.example.toikprojekt2022.repository.CartItemRepository;
import com.example.toikprojekt2022.repository.DiscountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class Test_CartItemService {

    private  final DiscountRepository discountRepository;
    private final CartItemRepository cartItemRepository;

    public Test_CartItemService(DiscountRepository discountRepository, CartItemRepository cartItemRepository) {
        this.discountRepository = discountRepository;
        this.cartItemRepository = cartItemRepository;
    }



    @Test
    public void findCartItemsWithDiscountPriceByOwnersLogin(){

    }
}
