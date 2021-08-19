package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findBooksByAgeRestriction(AgeRestriction ageRestriction);

    List<String> findAllGoldenBooks();

    List<String> booksByPriceLessThanFiveOrHigherThanForty(BigDecimal lower,BigDecimal upper);

    List<String> findAllByReleaseDateNotIn(int year);

    List<String> findAllByReleasedDateBefore(LocalDate date);
}
