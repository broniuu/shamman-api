package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.DiscountDto;
import org.springframework.data.domain.Page;

import java.util.List;
/**
 * Interfejs do obsługi klasy CartItem
 */
public interface IDiscountService {
    Page<DiscountDto> findDiscountDtos(int pageNumber);
}
