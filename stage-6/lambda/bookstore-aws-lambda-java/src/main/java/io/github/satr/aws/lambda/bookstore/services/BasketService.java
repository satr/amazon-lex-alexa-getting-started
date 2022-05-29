package io.github.satr.aws.lambda.bookstore.services;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.common.OperationResult;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import java.util.List;

public interface BasketService {
    List<Book> getBooks();
    Book getBookByIsbn(String isbn);
    OperationResult add(Book book);
    void clearBasket();
    void remove(Book book);
    int getBookCount();
}
