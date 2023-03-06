package com.example.toikprojekt2022.controler;

import com.example.toikprojekt2022.dto.DiscountToViewDto;
import com.example.toikprojekt2022.dto.UnlockDiscountDto;
import com.example.toikprojekt2022.repository.DiscountRepository;
import com.example.toikprojekt2022.repository.UserRepository;
import com.example.toikprojekt2022.service.DiscountService;
import com.example.toikprojekt2022.service.IDiscountService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Klasa obsługuje endpointy związane zniżkami
 */
@RestController
public class DiscountController {
    private final IDiscountService discountService;
    public DiscountController(DiscountRepository discountRepository, UserRepository userRepository) {
        this.discountService = new DiscountService(discountRepository, userRepository);
    }

    /**
     * Zwraca listę zniżek
     *
     * @param pageNumber    numer obecnej strony
     * @return              lista zniżek
     */
    @GetMapping(value = "/discounts",params = {"p"})
    public ResponseEntity<Page<DiscountToViewDto>> getDiscounts(
            @RequestParam(value = "p", defaultValue = "0") int pageNumber){
        Page<DiscountToViewDto> discountDto = discountService.findDiscountDtos(pageNumber);
        return new ResponseEntity<>(discountDto, HttpStatus.OK);
    }

    @PostMapping(value = "/discounts/{discountId}/unlockDiscount")
    public ResponseEntity unlockDiscount(
            @RequestBody UnlockDiscountDto unlockDiscountDto,
            @PathVariable UUID discountId) {
        boolean unlockingDiscountSuccess = discountService.tryUnlockDiscount(
                discountId, unlockDiscountDto.getUnlockCode(), unlockDiscountDto.getUserLogin());
        HttpStatus status = unlockingDiscountSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity(status);
    }
}
