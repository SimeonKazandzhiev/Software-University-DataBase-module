package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo,Long> {

    List<Shampoo> findAllBySize(Size size);

    List<Shampoo> findAllBySizeOrLabelIdOrderByPrice(Size size, Long labelId);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    @Query("SELECT count(s) FROM Shampoo s where s.price < :priceGiven")
    int findAllCountByPriceLessThan(@Param(value = "priceGiven") BigDecimal price);

    @Query("select s from Shampoo s join s.ingredients i where i.name In :names")
    List<Shampoo> findAllByIngredientsNames(@Param(value = "names")List<String> names);

    @Query("select s FROM Shampoo s join s.ingredients i group by s.id HAVING count(i) < :input")
    List<Shampoo> countShampoosByIngredientsLessThan(@Param(value = "input")long ingredients);

}