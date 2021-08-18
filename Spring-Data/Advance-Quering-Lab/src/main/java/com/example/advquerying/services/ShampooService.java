package com.example.advquerying.services;

import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {
    List<String> findAllShampoosByGivenSize(Size size);

    List<String> findAllBySizeOrLabel(Size size, Long id);

    List<String > findShampoosByPriceHigherThan(BigDecimal price);

     int findAllShampoosCountByGivenPrice(BigDecimal price);

    List<String> findAllShampoosByIngredients(List<String> params);

    List<String> findAllByGivenCountOfIngredients(long input);
}
