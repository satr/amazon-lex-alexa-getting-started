package io.github.satr.aws.lambda.bookstore.services;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.common.OperationResult;
import io.github.satr.aws.lambda.bookstore.common.OperationResultImpl;
import io.github.satr.aws.lambda.bookstore.entity.Book;

import java.util.LinkedList;
import java.util.List;

public class BasketServiceImpl implements BasketService {
    private List<Book> books = new LinkedList<>();

    @Override
    public List<Book> getBooks() {
        return books;
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return isbn == null || isbn.trim().isEmpty()
                ? null
                : books.stream().filter(book -> isbn.equals(book.getIsbn())).findFirst().orElse(null);
    }

    @Override
    public OperationResult add(Book book) {
        OperationResultImpl result = new OperationResultImpl();
        if(getBookByIsbn(book.getIsbn()) != null) {
            result.addError("This book is already in basket.");
            return result;
        }
        books.add(book);
        return result;
    }

    @Override
    public void clearBasket() {
        books.clear();
    }

    @Override
    public void remove(Book book) {
        books.remove(book);
    }

    @Override
    public int getBookCount() {
        return books.size();
    }
}
