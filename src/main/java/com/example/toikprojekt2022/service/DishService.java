package com.example.toikprojekt2022.service;

import com.example.toikprojekt2022.dto.DishDto;
import com.example.toikprojekt2022.exception.DishNotFoundException;
import com.example.toikprojekt2022.exception.RestarurantNotFoundException;
import com.example.toikprojekt2022.model.Dish;
import com.example.toikprojekt2022.repository.DishRepository;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
@Service
public class DishService implements IDishService {
    private final DishRepository dishRepository;
    private final Mapper mapper;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
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

}
