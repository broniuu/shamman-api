package com.example.toikprojekt2022.controler;

import com.example.toikprojekt2022.dto.DiscountDto;
import com.example.toikprojekt2022.repository.DiscountRepository;
import com.example.toikprojekt2022.service.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountRepository discountRepository) {
        this.discountService = new DiscountService(discountRepository);
    }

    @GetMapping("/discounts")
    public ResponseEntity<List<DiscountDto>> getDiscounts(){
        List<DiscountDto> discountDto = discountService.findDiscountDtos();
        return new ResponseEntity<>(discountDto, HttpStatus.OK);
    }
}
