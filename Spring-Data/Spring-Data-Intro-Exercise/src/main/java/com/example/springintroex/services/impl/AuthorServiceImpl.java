package com.example.springintroex.services.impl;

import com.example.springintroex.models.Author;
import com.example.springintroex.repositories.AuthorRepository;
import com.example.springintroex.services.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String AUTHOR_FILE_PATH = "src/main/resources/files/authors.txt";

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {

        if(authorRepository.count() > 0){
            return;
        }


        Files.readAllLines(Path.of(AUTHOR_FILE_PATH))
             .forEach(author -> {
                 String[] authorFullName =  author.split("\\s+");
                 Author author1 = new Author(authorFullName[0],authorFullName[1]);

                 this.authorRepository.save(author1);

             });

    }

    @Override
    public Author getRandomAuthor() {
        long randomId = ThreadLocalRandom
                .current()
                .nextLong(1,authorRepository.count() + 1);

        return authorRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<String> getAllAuthorsOrderByCountOfBooks() {
        return authorRepository
                .findAllByBooksSizeDESC()
                .stream()
                .map(author -> String.format("%s %s %d",
                        author.getFirstName(),
                        author.getLastName(),
                        author.getBooks().size()))
                .collect(Collectors.toList());
    }
}
