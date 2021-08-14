package com.example.springintroex.services.impl;

import com.example.springintroex.models.*;
import com.example.springintroex.repositories.BookRepository;
import com.example.springintroex.services.AuthorService;
import com.example.springintroex.services.BookService;
import com.example.springintroex.services.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if(bookRepository.count() > 0){
            return;
        }

        Files.readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookData = row.split("\\s+");

                    Book book = createBookFromInput(bookData);

                    bookRepository.save(book);

                });

    }

    private Book createBookFromInput(String[] bookData) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookData[0])];
        LocalDate releaseDate = LocalDate.parse(bookData[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookData[2]);
        BigDecimal price = new BigDecimal(bookData[3]);
        AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(bookData[4])];
        String title = Arrays.stream(bookData)
                        .skip(5)
                        .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();

        Set<Category> categories = categoryService.getRandomCategories();

        return new Book(editionType,releaseDate,copies,price,ageRestriction,title,author,categories);
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {

        return bookRepository.findAllByReleaseDateAfter(LocalDate.of(year,12,31));
    }

    @Override
    public List<String> findAllAuthorsWithReleaseDateBeforeYear(int year) {
        return this.bookRepository.findAllByReleaseDateBefore(LocalDate.of(year,1,1))
                .stream()
                .map(book -> String.format("%s %s",book.getAuthor().getFirstName(),book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFullNameOrderByReleaseDate(String fName, String lName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitleDesc(fName,lName)
                .stream()
                .map(book -> String.format("%s %s %d",book.getTitle(),book.getReleaseDate(),book.getCopies()))
                .collect(Collectors.toList());
    }



}


