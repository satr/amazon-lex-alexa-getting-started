package io.github.satr.aws.lambda.bookstore.common;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;

public final class TestHelper {
    public static boolean isEqual(Book b1, Book b2) {
        return b2.getIsbn().equals(b1.getIsbn())
                && b2.getTitle().equals(b1.getTitle())
                && b2.getAuthor().equals(b1.getAuthor())
                && b2.getIssueYear() == (b1.getIssueYear())
                && Double.compare(b2.getPrice(), b1.getPrice()) == 0;
    }
}
