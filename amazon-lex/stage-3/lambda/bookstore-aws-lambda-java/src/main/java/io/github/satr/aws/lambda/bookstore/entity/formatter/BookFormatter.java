package io.github.satr.aws.lambda.bookstore.entity.formatter;

import io.github.satr.aws.lambda.bookstore.entity.Book;

public final class BookFormatter {
    public static String getFullDescription(Book book, String newLineDelimiter) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("  Title: \"%s\"%s", book.getTitle(), newLineDelimiter));
        builder.append(String.format(" Author: %s%s", book.getAuthor(), newLineDelimiter));
        builder.append(String.format(" Issued: %s%s", book.getIssueYear(), newLineDelimiter));
        builder.append(String.format("   ISBN: %s%s", book.getIsbn(), newLineDelimiter));
        return null;
    }

    public static String getShortDescription(Book book) {
        return String.format("\"%s\" by %s", book.getTitle(), book.getAuthor());
    }
}
