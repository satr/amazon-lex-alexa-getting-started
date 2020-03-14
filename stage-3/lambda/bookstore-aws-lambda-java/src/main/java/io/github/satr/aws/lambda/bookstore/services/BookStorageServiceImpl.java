package io.github.satr.aws.lambda.bookstore.services;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookStorageServiceImpl implements BookStorageService {
    private List<Book> books = new LinkedList<>();

    public BookStorageServiceImpl() {
        addExampleBook("Monday follows Sunday", "John Smith", 1520, 7.2);
        addExampleBook("Sunday followed by Monday", "Jerry Smith", 1980, 20.0);
        addExampleBook("Tuesday is after Monday", "Anna Johnson", 2010, 15.5);
        addExampleBook("Friday is last working week day", "Anna Johnson", 2020, 9.99);
        addExampleBook("Thursday newer ends", "Peter Perkins", 1950, 4.8);
        addExampleBook("Beautiful Saturday", "John Peterson", 1963, 10.5);
        addExampleBook("Saturday and Sunday are best among a week days", "Jerry Wilson", 2005, 6.4);
        addExampleBook("It is a Sunday", "Jerry Wilson", 1830, 30.0);
        addExampleBook("It is Monday again", "John Smith", 1501, 150.0);
        addExampleBook("Wednesday is followed by Thursday and Friday", "Alice Smith", 1970, 3.1);
    }

    private boolean addExampleBook(String title, String author, int issuedIn, double price) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIssueYear(issuedIn);
        book.setPrice(price);
        return books.add(book);
    }

    @Override
    public List<Book> getBooksWithTitleContaining(String text) {
        return searchFor(item -> item.getTitle().toLowerCase().contains(text.toLowerCase()));
    }

    @Override
    public List<Book> getBooksWithTitleStartingWith(String text) {
        return searchFor(item -> item.getTitle().toLowerCase().startsWith(text.toLowerCase()));
    }

    @Override
    public List<Book> getBooksWithTitleEndingWith(String text) {
        return searchFor(item -> item.getTitle().toLowerCase().endsWith(text.toLowerCase()));
    }

    @Override
    public List<Book> getBooksWithTitle(String text) {
        return searchFor(item -> item.getTitle().toLowerCase().equals(text.toLowerCase()));
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return isbn == null || isbn.trim().isEmpty()
                ? null
                : books.stream().filter(book -> isbn.equals(book.getIsbn())).findFirst().orElse(null);
    }

    private List<Book> searchFor(Predicate<Book> containsPredicate) {
        return books.stream().filter(containsPredicate).collect(Collectors.toList());
    }
}
