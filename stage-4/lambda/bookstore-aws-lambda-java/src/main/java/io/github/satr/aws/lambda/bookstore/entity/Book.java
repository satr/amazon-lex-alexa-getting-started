package io.github.satr.aws.lambda.bookstore.entity;
// Copyright © 2020, github.com/satr, MIT License

import java.util.UUID;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private int issueYear;
    private double price = 0.0;

    public Book() {
        this.isbn = String.valueOf(Math.abs(UUID.randomUUID().toString().hashCode()));//fake ISBN
    }

    protected void copyFrom(Book book) {
        setIsbn(book.getIsbn());
        setAuthor(book.getAuthor());
        setTitle(book.getTitle());
        setIssueYear(book.getIssueYear());
        setPrice(book.getPrice());
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIssueYear(int issueYear) {
        this.issueYear = issueYear;
    }

    public int getIssueYear() {
        return issueYear;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
