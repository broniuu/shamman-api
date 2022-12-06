package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.DiscountDto;

import java.util.List;

public interface IDiscountService {
    List<DiscountDto> findDiscountDtos();
}
