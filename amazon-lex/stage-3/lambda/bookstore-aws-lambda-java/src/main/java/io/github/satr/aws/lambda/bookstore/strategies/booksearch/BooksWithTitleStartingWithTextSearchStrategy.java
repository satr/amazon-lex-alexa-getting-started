package io.github.satr.aws.lambda.bookstore.strategies.booksearch;

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;

import java.util.List;

public class BooksWithTitleStartingWithTextSearchStrategy extends AbstractBooksSearchStrategy {
    public BooksWithTitleStartingWithTextSearchStrategy(BookStorageService bookStorageService) {
        super(bookStorageService);
    }

    @Override
    public List<Book> queryBy(String titleQuery) {
        return bookStorageService.getBooksWithTitleStartingWith(titleQuery);
    }
}
