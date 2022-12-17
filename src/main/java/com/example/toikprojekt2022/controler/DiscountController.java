package com.example.toikprojekt2022.controler;

import com.example.toikprojekt2022.dto.DiscountDto;
import com.example.toikprojekt2022.repository.DiscountRepository;
import com.example.toikprojekt2022.service.DiscountService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiscountController {
    private final DiscountService discountService;
    public DiscountController(DiscountRepository discountRepository) {
        this.discountService = new DiscountService(discountRepository);
    }

    @GetMapping(value = "/discounts",params = {"p"})
    public ResponseEntity<Page<DiscountDto>> getDiscounts(
            @RequestParam(value = "p", defaultValue = "0") int pageNumber){
        Page<DiscountDto> discountDto = discountService.findDiscountDtos(pageNumber);
        return new ResponseEntity<>(discountDto, HttpStatus.OK);
    }
}
