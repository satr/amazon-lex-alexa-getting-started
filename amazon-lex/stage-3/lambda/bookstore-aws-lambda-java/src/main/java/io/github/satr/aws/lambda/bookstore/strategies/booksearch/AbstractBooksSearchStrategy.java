package io.github.satr.aws.lambda.bookstore.strategies.booksearch;

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;

import java.util.List;

public abstract class AbstractBooksSearchStrategy implements BookSearchStrategy {
    protected final BookStorageService bookStorageService;

    public AbstractBooksSearchStrategy(BookStorageService bookStorageService) {
        this.bookStorageService = bookStorageService;
    }

    @Override
    public abstract List<Book> queryBy(String titleQuery);
}
