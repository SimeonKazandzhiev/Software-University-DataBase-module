package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {

    List<Ingredient> findAllByNameStartsWith(String name);

    @Query("delete  FROM Ingredient i where i.name = :name")
    @Modifying
    void deleteAllByName(@Param(value = "name")String name);

    @Query("update Ingredient i SET i.price = i.price * 1.10")
    @Modifying
    void updateAllIngredientPrice();

    @Query("update Ingredient i set i.price = i.price + 100 where i.name IN :names")
    @Modifying
    void updateAllIngredientsByGivenName(@Param(value = "names")List<String> params);
}
