package io.github.satr.aws.lambda.bookstore.strategies.booksearch;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;

import java.util.List;

public class BooksWithTitleEndingWithTextSearchStrategy extends AbstractBooksSearchStrategy {
    public BooksWithTitleEndingWithTextSearchStrategy(BookStorageService bookStorageService) {
        super(bookStorageService);
    }

    @Override
    public List<Book> queryBy(String titleQuery) {
        return bookStorageService.getBooksWithTitleEndingWith(titleQuery);
    }
}
