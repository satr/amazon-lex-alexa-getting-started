package io.github.satr.aws.lambda.bookstore.repositories;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;

import java.util.List;

public interface CustomerBooksRepository {
    void clearBookSearchResult();
    void putToBookSearchResult(List<? extends Book> books);
    List<Book> getBasketBooks();
    List<Book> getBookSearchResult();
    void addToBasket(Book book);
    void clearBasket();
    void removeFromBasket(Book book);
    int getBasketSize();
}
