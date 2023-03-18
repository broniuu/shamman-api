package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.CartItemDto;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

/**
 * Obsługuje operacje związane z zarządzaniem zniżkami
 */
@Service
@Transactional
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
        Dish dishWithThisDiscount = dishRepository.findById(discount.getDishId()).orElseThrow(() ->
                new RuntimeException("Nie istnieje danie z tą zniżką!")
        );
        DishWithDiscountDto dishWithDiscountDto = new DishWithDiscountDto();
        dishWithDiscountDto.setDishId(dishWithThisDiscount.getDishId());
        dishWithDiscountDto.setName(dishWithThisDiscount.getName());
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double oldPrize = dishWithThisDiscount.getPrice();
        String formattedOldPrize = decimalFormat.format(oldPrize);
        dishWithDiscountDto.setOldPrice(formattedOldPrize);
        double discountValue = discount.getDiscountValue();
        dishWithDiscountDto.setPercentageDiscount(discountValue);
        double moneySavedOnDiscount = oldPrize * discountValue;
        String formattedMoneySavedOnDiscount = decimalFormat.format(moneySavedOnDiscount);
        dishWithDiscountDto.setSavedMoney(formattedMoneySavedOnDiscount);
        double newPrize = oldPrize - moneySavedOnDiscount;
        String formattedNewPrize = decimalFormat.format(newPrize);
        dishWithDiscountDto.setNewPrice(formattedNewPrize);
        return dishWithDiscountDto;
    }
    @Override
    public List<DiscountDto> getDiscountsOfUser(String userLogin) {
        List<Discount> discounts = discountRepository.findAllByUsersWhoUsedThisDiscount(userLogin);
        List<DiscountDto> discountDtos = discounts.stream().map(x -> mapper.map(x, DiscountDto.class)).toList();
        if (discountDtos.size() == 0) throw new ResourceNotFoundException("Nie znaleziono zniżek dla tego użytkownika");
        return discountDtos;
    }
    @Override
    public void saveUsedDiscountWithItsOwner(String discountCode, String ownersLogin, List<CartItemDto> cartItemDtos){
        Discount discount = discountRepository.findByDiscountCode(discountCode)
                .orElseThrow(() -> new RuntimeException("Zniżka o podanym kodzie nie istnieje"));
        if (cartItemDtos.stream().anyMatch(x -> x.getDish().getDishId() == discount.getDishId()))
            throw new RuntimeException("Zniżka nie dotyczy żadnego z wybranych produktów!");
        User discountOwner = userRepository.findByLogin(ownersLogin);
        if (discountOwner == null)
            throw new RuntimeException("Ten użytkownik nie istnieje");
        List<User> usersWithThisDiscount = discount.getUsersWhoUsedThisDiscount();
        usersWithThisDiscount.add(discountOwner);
        discount.setUsersWhoUsedThisDiscount(usersWithThisDiscount);
        discountRepository.save(discount);
    }
}
