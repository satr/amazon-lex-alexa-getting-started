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
        addExampleBook("Monday follows Sunday", "John Smith", 1520);
        addExampleBook("Sunday followed by Monday", "Jerry Smith", 1980);
        addExampleBook("Tuesday is after Monday", "Anna Johnson", 2010);
        addExampleBook("Friday is last working week day", "Anna Johnson", 2020);
        addExampleBook("Thursday newer ends", "Peter Perkins", 1950);
        addExampleBook("Beautiful Saturday", "John Peterson", 1963);
        addExampleBook("Saturday and Sunday are best among a week days", "Jerry Wilson", 2005);
        addExampleBook("It is a Sunday", "Jerry Wilson", 1830);
        addExampleBook("It is Monday again", "John Smith", 1501);
        addExampleBook("Wednesday is followed by Thursday and Friday", "Alice Smith", 1970);
    }

    private boolean addExampleBook(String title, String author, int issuedIn) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIssueYear(issuedIn);
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

    private List<Book> searchFor(Predicate<Book> containsPredicate) {
        return books.stream().filter(containsPredicate).collect(Collectors.toList());
    }
}
