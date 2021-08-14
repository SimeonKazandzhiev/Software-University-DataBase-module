package com.example.springintroex;


import com.example.springintroex.models.Book;
import com.example.springintroex.services.AuthorService;
import com.example.springintroex.services.BookService;
import com.example.springintroex.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public ConsoleRunner(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
       // printAllBooksAfter2000(1990);

      //  printAllAuthorsNamesWithBooksBeforeYear(1990);

      //  printAllAuthorsAndNumberOfTheirBooks();

        printAllBooksByAuthorNameOrderByRelDate("George","Powell");

    }

    private void printAllBooksByAuthorNameOrderByRelDate(String firstName, String lastName) {

        bookService.findAllBooksByAuthorFullNameOrderByReleaseDate(firstName,lastName)
        .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksBeforeYear(int year) {
        bookService.findAllAuthorsWithReleaseDateBeforeYear(year)
        .forEach(System.out::println);

    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }

    private void printAllBooksAfter2000(int year) {

        bookService.findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);

    }
}
