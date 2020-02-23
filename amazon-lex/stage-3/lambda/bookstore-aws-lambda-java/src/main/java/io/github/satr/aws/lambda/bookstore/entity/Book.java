package io.github.satr.aws.lambda.bookstore.entity;

import java.util.UUID;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private int issueYear;

    public Book() {
        this.isbn = String.valueOf(UUID.randomUUID().toString().hashCode());
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
}
