package io.github.satr.aws.lambda.bookstore.entity.formatter;

import io.github.satr.aws.lambda.bookstore.entity.Book;

public class LexMessageFormatter extends MessageFormatter {
    @Override
    public String getBookFullDescription(Book book, String newLineDelimiter) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("  Title: \"%s\"%s", book.getTitle(), newLineDelimiter));
        builder.append(String.format(" Author: %s%s", book.getAuthor(), newLineDelimiter));
        builder.append(String.format(" Issued: %s%s", book.getIssueYear(), newLineDelimiter));
        builder.append(String.format("   ISBN: %s%s", book.getIsbn(), newLineDelimiter));
        builder.append(String.format("  Price: %.2f%s", book.getPrice(), newLineDelimiter));
        return builder.toString();
    }

    @Override
    protected void addBookDescriptionToList(StringBuilder messageBuilder, Book book, boolean showBookNumber, int bookNumber, boolean withPrices) {
        if (showBookNumber)
            messageBuilder.append(String.format("%d. ", bookNumber));
        messageBuilder.append(String.format("\"%s\" by %s", book.getTitle(), book.getAuthor()));
        if(withPrices)
            messageBuilder.append(String.format("; Price: %.2f", book.getPrice()));
        messageBuilder.append("\n");
    }

    @Override
    public String getPriceText(double price) {
        return String.format("%.2f", price);
    }

    @Override
    public String getShortBreak() {
        return "";
    }
}
