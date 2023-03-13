package com.example.toikprojekt2022.dto;

import com.example.toikprojekt2022.model.Dish;
import com.example.toikprojekt2022.model.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class DiscountDto {
    private UUID discountId;
    private UUID dishId;
    private double discountValue;
    private String discountCode;
    private LocalDate expireDate;
    private Dish dish;
    List<User> userWithThisDiscount;
}
