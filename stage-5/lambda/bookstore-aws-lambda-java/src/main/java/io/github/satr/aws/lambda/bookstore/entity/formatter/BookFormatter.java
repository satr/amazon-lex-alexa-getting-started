package io.github.satr.aws.lambda.bookstore.entity.formatter;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;

public final class BookFormatter {
    public static String getFullDescription(Book book, String newLineDelimiter) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("  Title: \"%s\"%s", book.getTitle(), newLineDelimiter));
        builder.append(String.format(" Author: %s%s", book.getAuthor(), newLineDelimiter));
        builder.append(String.format(" Issued: %s%s", book.getIssueYear(), newLineDelimiter));
        builder.append(String.format("   ISBN: %s%s", book.getIsbn(), newLineDelimiter));
        builder.append(String.format("  Price: %.2f%s", book.getPrice(), newLineDelimiter));
        return builder.toString();
    }

    public static String getShortDescription(Book book) {
        return String.format("\"%s\" by %s", book.getTitle(), book.getAuthor());
    }
}
