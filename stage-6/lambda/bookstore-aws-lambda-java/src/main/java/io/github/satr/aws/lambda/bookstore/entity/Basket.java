package io.github.satr.aws.lambda.bookstore.entity;
// Copyright © 2022, github.com/satr, MIT License

import java.util.LinkedList;
import java.util.List;

public class Basket {
    private List<Book> orderedBooks = new LinkedList<>();

    public boolean containsIsbn(String isbn) {
        return orderedBooks.stream().anyMatch(book -> book.getIsbn().equals(isbn));
    }

    public void add(Book book) {
        orderedBooks.add(book);
    }
}
