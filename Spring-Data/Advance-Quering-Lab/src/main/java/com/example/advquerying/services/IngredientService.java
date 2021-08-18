package com.example.advquerying.services;


import java.util.List;

public interface IngredientService {
    List<String> findIngredientByGivenStr(String startsWith);

    void deleteIngredientByGivenName(String ingredient);

    void updateAllIngredientPrices();

    void updateAllIngredientsByGivenNames(List<String> ingredients);
}
