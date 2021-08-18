package com.example.advquerying.services.impl;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import com.example.advquerying.services.ShampooService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {

    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }


    @Override
    public List<String> findAllShampoosByGivenSize(Size size) {
        return shampooRepository.findAllBySize(size)
                .stream()
                .map(shampoo -> String.format("%s %s %.2f",shampoo.getBrand()
                ,shampoo.getSize(),shampoo.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBySizeOrLabel(Size size, Long id) {
        return this.shampooRepository.findAllBySizeOrLabelIdOrderByPrice(size,id)
                .stream()
                .map(shampoo -> String.format("%s %s %.2f",shampoo.getBrand(),
                        shampoo.getSize().name(),shampoo.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findShampoosByPriceHigherThan(BigDecimal price) {
        return this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price)
                .stream()
                .map(shampoo -> String.format("%s %s %.2f",
                        shampoo.getBrand(),
                        shampoo.getSize().name(),
                        shampoo.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public int findAllShampoosCountByGivenPrice(BigDecimal price) {

        return this.shampooRepository.findAllCountByPriceLessThan(price);
    }

    @Override
    public List<String> findAllShampoosByIngredients(List<String> params) {

        return this.shampooRepository.findAllByIngredientsNames(params)
                .stream()
                .map(Shampoo::getBrand)
                .collect(Collectors.toList());

    }

    @Override
    public List<String> findAllByGivenCountOfIngredients(long input) {
        return this.shampooRepository.countShampoosByIngredientsLessThan(input)
                .stream().map(Shampoo::getBrand).collect(Collectors.toList());
    }
}
