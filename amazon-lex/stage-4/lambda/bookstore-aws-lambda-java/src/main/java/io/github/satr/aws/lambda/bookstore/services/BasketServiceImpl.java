package io.github.satr.aws.lambda.bookstore.services;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.common.OperationResult;
import io.github.satr.aws.lambda.bookstore.common.OperationResultImpl;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.repositories.CustomerBooksRepository;

import java.util.List;

public class BasketServiceImpl implements BasketService {
    private final CustomerBooksRepository customerBooksRepository;

    public BasketServiceImpl(CustomerBooksRepository customerBooksRepository) {
        this.customerBooksRepository = customerBooksRepository;
    }

    @Override
    public List<Book> getBooks() {
        return customerBooksRepository.getBasketBooks();
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return isbn == null || isbn.trim().isEmpty()
                ? null
                : getBooks().stream().filter(book -> isbn.equals(book.getIsbn())).findFirst().orElse(null);
    }

    @Override
    public OperationResult add(Book book) {
        OperationResultImpl result = new OperationResultImpl();
        if(getBookByIsbn(book.getIsbn()) != null) {
            result.addError("This book is already in basket.");
            return result;
        }
        customerBooksRepository.addToBasket(book);
        return result;
    }

    @Override
    public void clearBasket() {
        customerBooksRepository.clearBasket();
    }

    @Override
    public void remove(Book book) {
        customerBooksRepository.removeFromBasket(book);
    }

    @Override
    public int getBookCount() {
        return customerBooksRepository.getBasketSize();
    }
}
