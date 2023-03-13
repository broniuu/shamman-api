package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.DiscountDto;
import com.example.toikprojekt2022.dto.DiscountToViewDto;
import com.example.toikprojekt2022.exception.DishNotFoundException;
import com.example.toikprojekt2022.exception.ResourceNotFoundException;
import com.example.toikprojekt2022.extension.DiscountExtension;
import com.example.toikprojekt2022.model.Discount;
import com.example.toikprojekt2022.model.User;
import com.example.toikprojekt2022.repository.DiscountRepository;
import com.example.toikprojekt2022.repository.UserRepository;
import lombok.experimental.ExtensionMethod;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Obsługuje operacje związane z zarządzaniem zniżkami
 */
@ExtensionMethod({DiscountExtension.class})
public class DiscountService implements IDiscountService {
    private DiscountRepository discountRepository;
    private UserRepository userRepository;
    private final Mapper mapper;
    public DiscountService(DiscountRepository discountRepository, UserRepository userRepository) {
        this.discountRepository = discountRepository;
        this.userRepository = userRepository;
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    /**
     * Pobiera wszystkie zniżki na danej stronie
     *
     * @param pageNumber    numer strony
     * @return              wszystkie zniżki na danej stronie
     */
    @Override
    public Page<DiscountToViewDto> findDiscountDtos(int pageNumber) {
        final int pageSize = 10;
        List<Discount> discounts = (List<Discount>) discountRepository.findAll();
        int discountsTotal = discounts.size();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Discount> discountPage = new PageImpl<>(discounts,pageable,discountsTotal);
        if (discounts.isEmpty() || discountPage.getTotalElements() > discountsTotal)
            throw new DishNotFoundException("There is no discounts on this page");
        return discountPage.map(discount -> discount.toDiscountDto());
    }

    @Override
    public boolean tryUnlockDiscount(UUID discountId, String discountCode, String login) {
        Optional<Discount> probableDiscount = discountRepository.findById(discountId);
        if(probableDiscount.isEmpty())
            throw new RuntimeException("Zniżka o podanym Id nie istnieje");
        Discount discount = probableDiscount.get();
        if (!discount.getDiscountCode().equals(discountCode))
            throw new RuntimeException("Podano nieporpawny Kod Rabatowy");
        User user = userRepository.findByLogin(login);
        if(user == null)
            throw new RuntimeException("Użytkownik o podanym loginie nie istnieje!");
        List<Discount> usersDiscounts = user.getDiscounts();
        usersDiscounts.add(discount);
        user.setDiscounts(usersDiscounts);
        userRepository.save(user);
        return true;
    }

    @Override
    public List<DiscountDto> getDiscountsOfUser(String userLogin){
        List<Discount> discounts = discountRepository.findByUserWithThisDiscount_Login(userLogin);
        List<DiscountDto> discountDtos = discounts.stream().map(x -> mapper.map(x, DiscountDto.class)).toList();
        if (discountDtos == null) throw new RuntimeException("Błąd podczas pobierania danucych)");
        if (discountDtos.size() == 0) throw new ResourceNotFoundException("Nie znaleziono zniżek dla tego użytkownika");
        return discountDtos;
    }
}
