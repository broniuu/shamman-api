package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.DiscountDto;
import com.example.toikprojekt2022.exception.DishNotFoundException;
import com.example.toikprojekt2022.extension.DiscountExtension;
import com.example.toikprojekt2022.model.Discount;
import com.example.toikprojekt2022.repository.DiscountRepository;
import lombok.experimental.ExtensionMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@ExtensionMethod({DiscountExtension.class})
public class DiscountService implements IDiscountService {
    private DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public List<DiscountDto> findDiscountDtos() {
        return null;
    }
    @Override
    public Page<DiscountDto> findDiscountDtos(int pageNumber) {
        final int pageSize = 10;
        List<Discount> discounts = (List<Discount>) discountRepository.findAll();
        int discountsTotal = discounts.size();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Discount> discountPage = new PageImpl<>(discounts,pageable,discountsTotal);
        if (discounts.isEmpty() || discountPage.getTotalElements() > discountsTotal)
            throw new DishNotFoundException("There is no discounts on this page");
        return discountPage.map(discount -> discount.toDiscountDto());
    }
}
