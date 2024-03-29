package io.github.satr.aws.lambda.bookstore.services;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;
import java.util.List;

public interface BookStorageService {
    List<Book> getBooksWithTitleStartingWith(String text);
    List<Book> getBooksWithTitleEndingWith(String text);
    List<Book> getBooksWithTitleContaining(String text);
    List<Book> getBooksWithTitle(String text);
    Book getBookByIsbn(String isbn);
}
