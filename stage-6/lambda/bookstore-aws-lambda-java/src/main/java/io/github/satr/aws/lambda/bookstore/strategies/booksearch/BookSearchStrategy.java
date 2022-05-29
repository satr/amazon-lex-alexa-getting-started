package io.github.satr.aws.lambda.bookstore.strategies.booksearch;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;

import java.util.List;

public interface BookSearchStrategy {
    List<Book> queryBy(String titleQuery);
}
