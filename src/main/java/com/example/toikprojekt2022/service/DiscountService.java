package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.DiscountDto;
import com.example.toikprojekt2022.dto.DiscountToViewDto;
import com.example.toikprojekt2022.dto.DishWithDiscountDto;
import com.example.toikprojekt2022.exception.DishNotFoundException;
import com.example.toikprojekt2022.exception.ResourceNotFoundException;
import com.example.toikprojekt2022.extension.DiscountExtension;
import com.example.toikprojekt2022.model.Discount;
import com.example.toikprojekt2022.model.Dish;
import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.repository.DiscountRepository;
import com.example.toikprojekt2022.repository.DishRepository;
import com.example.toikprojekt2022.repository.UserRepository;
import lombok.experimental.ExtensionMethod;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Obsługuje operacje związane z zarządzaniem zniżkami
 */
@ExtensionMethod({DiscountExtension.class})
public class DiscountService implements IDiscountService {
    private final DiscountRepository discountRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final DishRepository dishRepository;

    public DiscountService(DiscountRepository discountRepository, UserRepository userRepository, DishRepository dishRepository) {
        this.discountRepository = discountRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    /**
     * Pobiera wszystkie zniżki na danej stronie
     *
     * @param pageNumber numer strony
     * @return wszystkie zniżki na danej stronie
     */
    @Override
    public Page<DiscountToViewDto> findDiscountDtos(int pageNumber) {
        final int pageSize = 10;
        List<Discount> discounts = (List<Discount>) discountRepository.findAll();
        int discountsTotal = discounts.size();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Discount> discountPage = new PageImpl<>(discounts, pageable, discountsTotal);
        if (discounts.isEmpty() || discountPage.getTotalElements() > discountsTotal)
            throw new DishNotFoundException("There is no discounts on this page");
        return discountPage.map(discount -> discount.toDiscountDto());
    }

    @Override
    public DishWithDiscountDto tryUnlockDiscount(String discountCode, String login) {
        Discount discount = discountRepository.findByDiscountCode(discountCode)
                .orElseThrow(() -> new RuntimeException("Podano nieporpawny Kod Rabatowy"));
        User user = userRepository.findByLogin(login);
        if (user == null)
            throw new RuntimeException("Użytkownik o podanym loginie nie istnieje!");
        UUID discountId = discount.getDiscountId();
        boolean discountWasUsedByThisUser = discountRepository.existsByDiscountIdAndUsersWhoUsedThisDiscount(discountId, user);
        if (discountWasUsedByThisUser) {
            throw new RuntimeException("Użytkownik już wykorzystał tą zniżkę!");
        }
        List<User> usersWithThisDiscount = discount.getUsersWhoUsedThisDiscount();
        usersWithThisDiscount.add(user);
        discount.setUsersWhoUsedThisDiscount(usersWithThisDiscount);
        discountRepository.save(discount);
        Dish dishWithThisDiscount = dishRepository.findById(discount.getDishId()).orElseThrow(() ->
                new RuntimeException("Nie istnieje danie z tą zniżką!")
        );
        DishWithDiscountDto dishWithDiscountDto = new DishWithDiscountDto();
        dishWithDiscountDto.setDishId(dishWithDiscountDto.getDishId());
        dishWithDiscountDto.setName(dishWithThisDiscount.getName());
        double oldPrize = dishWithThisDiscount.getPrice();
        dishWithDiscountDto.setOldPrice(oldPrize);
        double discountValue = discount.getDiscountValue();
        dishWithDiscountDto.setPercentageDiscount(discountValue);
        double moneySavedOnDiscount = oldPrize * discountValue;
        dishWithDiscountDto.setSavedMoney(moneySavedOnDiscount);
        double newPrize = oldPrize - moneySavedOnDiscount;
        dishWithDiscountDto.setNewPrice(newPrize);
        return dishWithDiscountDto;
    }

    @Override
    public List<DiscountDto> getDiscountsOfUser(String userLogin) {
        List<Discount> discounts = discountRepository.findAllByUsersWhoUsedThisDiscount(userLogin);
        List<DiscountDto> discountDtos = discounts.stream().map(x -> mapper.map(x, DiscountDto.class)).toList();
        if (discountDtos.size() == 0) throw new ResourceNotFoundException("Nie znaleziono zniżek dla tego użytkownika");
        return discountDtos;
    }
}
