package com.example.advquerying.services.impl;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import com.example.advquerying.services.IngredientService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    public List<String> findIngredientByGivenStr(String startsWith) {
        return this.ingredientRepository.findAllByNameStartsWith(startsWith)
                .stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteIngredientByGivenName(String ingredient) {
        this.ingredientRepository.deleteAllByName(ingredient);
    }

    @Override
    @Transactional
    public void updateAllIngredientPrices() {
        this.ingredientRepository.updateAllIngredientPrice();
    }

    @Override
    @Transactional
    public void updateAllIngredientsByGivenNames(List<String> ingredients) {
        this.ingredientRepository.updateAllIngredientsByGivenName(ingredients);
    }
}
