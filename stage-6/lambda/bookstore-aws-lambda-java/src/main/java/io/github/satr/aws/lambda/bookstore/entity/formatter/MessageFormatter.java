package io.github.satr.aws.lambda.bookstore.entity.formatter;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;
import java.util.List;

public abstract class MessageFormatter {
    public abstract String getBookFullDescription(Book book, String newLineDelimiter);

    public static String getBookShortDescription(Book book) {
        return String.format("\"%s\" by %s", book.getTitle(), book.getAuthor());
    }

    public String getBookShortDescriptionList(List<Book> books) {
        return getBookShortDescriptionList(books, "");
    }

    public String getBookShortDescriptionListWithPrices(List<Book> books, String headingLineFormat, Object... args) {
        return getBookShortDescriptionList(true, books, headingLineFormat, args);
    }

    public String getBookShortDescriptionList(List<Book> books, String headingLineFormat, Object... args) {
        return getBookShortDescriptionList(false, books, headingLineFormat, args);
    }

    public String amountOfBooks(int amount) {
        return amount == 1 ? "one book" : String.format("%d books", amount);
    }

    private String getBookShortDescriptionList(boolean withPrices, List<Book> books, String headingLineFormat, Object... args) {
        StringBuilder messageBuilder = new StringBuilder(String.format(headingLineFormat, args));
        int bookCount = books.size();
        boolean showBookNumber = bookCount > 1;
        for (int i = 0; i < bookCount; i++) {
            int bookNumber = i + 1;
            addBookDescriptionToList(messageBuilder, books.get(i), showBookNumber, bookNumber, withPrices);
        }
        return messageBuilder.toString();
    }

    protected abstract void addBookDescriptionToList(StringBuilder messageBuilder, Book book, boolean showBookNumber, int bookNumber, boolean withPrices);

    public abstract String getPriceText(double price);

    public abstract String getShortBreak();
}
