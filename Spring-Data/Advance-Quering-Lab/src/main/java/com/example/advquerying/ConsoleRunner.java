package com.example.advquerying;

import com.example.advquerying.entities.Size;
import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final ShampooService shampooService;
    private final IngredientService ingredientService;
    private final BufferedReader bufferedReader;

    public ConsoleRunner(ShampooService shampooService, IngredientService ingredientService, BufferedReader bufferedReader) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
        this.bufferedReader = bufferedReader;
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Please select the problem number:");
        int problem = Integer.parseInt(bufferedReader.readLine());

        switch (problem) {
            case 1 -> findShampoosBySize();
            case 2 -> findShampoosBySizeOrLabel();
            case 3 -> shampoosByPrice();
            case 4 -> ingredientByName();
            case 6 -> countShampoos();
            case 7 -> shampoosByIngredient();
            case 8 -> shampoosByIngredientsCount();
            case 9 -> deleteIngredientsByName();
            case 10 -> updateIngredientPrice();
            case 11 -> updateIngredientPriceByGivenNames();

        }

    }

    private void updateIngredientPriceByGivenNames() throws IOException {
        System.out.println("Please enter ingredients names:");
        List<String> ingredients = Arrays.asList(bufferedReader.readLine().split("\\s+"));

        this.ingredientService.updateAllIngredientsByGivenNames(ingredients);

    }

    private void updateIngredientPrice() {

        this.ingredientService.updateAllIngredientPrices();

    }

    private void deleteIngredientsByName() throws IOException {
        System.out.println("Please enter ingredient to be deleted:");
        String ingredient = bufferedReader.readLine();

        this.ingredientService.deleteIngredientByGivenName(ingredient);


    }

    private void shampoosByIngredientsCount() throws IOException {
        System.out.println("Please enter the count of ingredients:");
        long input = Long.parseLong(bufferedReader.readLine());

        this.shampooService.findAllByGivenCountOfIngredients(input)
        .forEach(System.out::println);

    }

    private void shampoosByIngredient() throws IOException {
        System.out.println("Please enter ingredients names:");
        List<String> inputNames = Arrays.asList(bufferedReader.readLine()
                .split("\\s+"));

        this.shampooService.findAllShampoosByIngredients(inputNames)
        .forEach(System.out::println);

    }

    private void countShampoos() throws IOException {
        System.out.println("Please enter price:");
        BigDecimal priceInput = new BigDecimal(bufferedReader.readLine());

        System.out.println(this.shampooService.findAllShampoosCountByGivenPrice(priceInput));

    }

    private void ingredientByName() throws IOException {
        System.out.println("Please enter the letter starts with:");
        String str = bufferedReader.readLine();

        this.ingredientService.findIngredientByGivenStr(str)
                .forEach(System.out::println);

    }

    private void shampoosByPrice() throws IOException {
        System.out.println("Please enter price:");
        BigDecimal price = new BigDecimal(bufferedReader.readLine());

        this.shampooService.findShampoosByPriceHigherThan(price)
                .forEach(System.out::println);

    }

    private void findShampoosBySizeOrLabel() throws IOException {
        Size size = Size.valueOf(bufferedReader.readLine());
        Long labelId = Long.parseLong(bufferedReader.readLine());

        this.shampooService.findAllBySizeOrLabel(size, labelId)
                .forEach(System.out::println);


    }

    private void findShampoosBySize() throws IOException {
        Size size = (Size.valueOf(bufferedReader.readLine()));

        this.shampooService.findAllShampoosByGivenSize(size)
                .forEach(System.out::println);

    }
}
