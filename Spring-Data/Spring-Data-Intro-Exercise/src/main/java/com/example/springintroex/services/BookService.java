package com.example.springintroex.services;

import com.example.springintroex.models.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFullNameOrderByReleaseDate(String firstName, String lastName);


}

