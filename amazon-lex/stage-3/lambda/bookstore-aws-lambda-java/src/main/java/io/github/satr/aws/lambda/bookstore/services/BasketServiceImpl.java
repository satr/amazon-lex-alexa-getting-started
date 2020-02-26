package io.github.satr.aws.lambda.bookstore.services;

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
        for (Book book : books) {
            if (isbn != null && isbn.equals(book.getIsbn()))
                return book;
        }
        return null;
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
}
