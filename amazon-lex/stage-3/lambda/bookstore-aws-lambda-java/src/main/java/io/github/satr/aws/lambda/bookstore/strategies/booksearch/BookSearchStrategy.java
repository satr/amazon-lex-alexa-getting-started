package io.github.satr.aws.lambda.bookstore.strategies.booksearch;

import io.github.satr.aws.lambda.bookstore.entity.Book;

import java.util.List;

public interface BookSearchStrategy {
    List<Book> queryBy(String titleQuery);
}
