package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader bufferedReader;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, BufferedReader bufferedReader) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Please enter exercise number:");
        int task = Integer.parseInt(bufferedReader.readLine());

        switch (task) {
            case 1 -> booksTitlesByAgeRestriction();
            case 2 -> goldenBooks();
            case 3 -> booksByPrice();
            case 4 -> notReleasedBooks();
            case 5 -> bookReleasedBefore();
        }

    }

    private void bookReleasedBefore() throws IOException {
        System.out.println("Please enter date:");
        LocalDate date = LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        this.bookService
                .findAllByReleasedDateBefore(date)
                .forEach(System.out::println);

    }

    private void notReleasedBooks() throws IOException {
        System.out.println("Please enter year:");
        int year = Integer.parseInt(bufferedReader.readLine());

        this.bookService.findAllByReleaseDateNotIn(year).forEach(System.out::println);

    }

    private void booksByPrice() {
        this.bookService.booksByPriceLessThanFiveOrHigherThanForty(new BigDecimal(5), new BigDecimal(40))
                .forEach(System.out::println);

    }

    private void goldenBooks() {
        this.bookService.findAllGoldenBooks().forEach(System.out::println);
    }

    private void booksTitlesByAgeRestriction() throws IOException {
        System.out.println("Please enter age restriction(Minor,Teen,Adult):");
        AgeRestriction ageRestriction = AgeRestriction.valueOf(bufferedReader.readLine().toUpperCase(Locale.ROOT));

        this.bookService.findBooksByAgeRestriction(ageRestriction)
                .forEach(System.out::println);


    }

}
