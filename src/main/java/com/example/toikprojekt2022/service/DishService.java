package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.DishDto;
import com.example.toikprojekt2022.exception.DishNotFoundException;
import com.example.toikprojekt2022.model.Dish;
import com.example.toikprojekt2022.repository.DishRepository;
import com.example.toikprojekt2022.repository.RestaurantRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DishService implements IDishService {
    private final DishRepository dishRepository;
    private final Mapper mapper;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();

    }


    @Override
    public DishDto findByDishNameAndRestaurantId( UUID id,String name) {
        Dish dish= dishRepository.findByDishNameAndRestaurantId(id,name)
                .orElse(null);
        if(dish==null) throw new DishNotFoundException("Dish:"+name+" not Found in Restaurant");
        DishDto dishDto = new DishDto();

        mapper.map(dish,dishDto);
        return dishDto;
    }

    @Override
    public Iterable<DishDto> findByRestaurantId(UUID id) {
        List<Dish> dishes = (List<Dish>) dishRepository.findByRestaurantId(id);
        if (dishes.isEmpty()) throw new DishNotFoundException("There is no Dishes for this Restaurant. Your Restaurant ID may be wrong");
        List<DishDto> DishDtos = new ArrayList<>();
        for(Dish dish : dishes){
            DishDto dishDto = mapper.map(dish, DishDto.class);
            DishDtos.add(dishDto);
        }
        return DishDtos;
    }

    @Override
    public Page<DishDto> findDishesByRestaurantName(String restaurantName, int pageNumber) {
        final int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        UUID restaurantId = restaurantRepository.findRestaurantIdByName(restaurantName);
        int maxCountOfDishes = dishRepository.countDishesByRestaurantId(restaurantId);
        Page<Dish> pagedDishes = dishRepository.findDishesByRestaurantName(restaurantId, pageable);
        if (pagedDishes.isEmpty() || pagedDishes.getTotalElements() > maxCountOfDishes)
            throw new DishNotFoundException("There is no Dishes for this Restaurant on this page");
        return pagedDishes.map(dish -> mapper.map(dish, DishDto.class));
    }
}
