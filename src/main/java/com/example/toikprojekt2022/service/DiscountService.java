package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.DiscountDto;
import com.example.toikprojekt2022.exception.DishNotFoundException;
import com.example.toikprojekt2022.extension.DiscountExtension;
import com.example.toikprojekt2022.model.Discount;
import com.example.toikprojekt2022.repository.DiscountRepository;
import lombok.experimental.ExtensionMethod;

import java.util.List;

@ExtensionMethod({DiscountExtension.class})
public class DiscountService implements IDiscountService {
    private DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public List<DiscountDto> findDiscountDtos() {
        List<Discount> discounts = (List<Discount>) discountRepository.findAll();
        if (discounts.isEmpty()) throw new DishNotFoundException("There is no Dishes for this Restaurant. Your Restaurant ID may be wrong");
        return discounts.stream().map(discount -> discount.toDiscountDto()).toList();
    }
}
