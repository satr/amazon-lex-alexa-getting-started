package io.github.satr.aws.lambda.bookstore.entity.formatter;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;
import java.util.List;

public final class BookListFormatter {
    public static String getShortDescriptionList(List<Book> books) {
        return getShortDescriptionList(books, "");
    }

    public static String getShortDescriptionListWithPrices(List<Book> books, String headingLineFormat, Object... args) {
        return getShortDescriptionList(true, books, headingLineFormat, args);
    }

    public static String getShortDescriptionList(List<Book> books, String headingLineFormat, Object... args) {
        return getShortDescriptionList(false, books, headingLineFormat, args);
    }

    public static String amountOfBooks(int amount) {
        return amount == 1 ? "one book" : String.format("%d books", amount);
    }

    private static String getShortDescriptionList(boolean withPrices, List<Book> books, String headingLineFormat, Object... args) {
        StringBuilder messageBuilder = new StringBuilder(String.format(headingLineFormat, args));
        int bookCount = books.size();
        boolean showBookNumber = bookCount > 1;
        for (int i = 0; i < bookCount; i++) {
            int bookNumber = i + 1;
            addBookDescriptionToList(messageBuilder, books.get(i), showBookNumber, bookNumber, withPrices);
        }
        return messageBuilder.toString();
    }

    private static void addBookDescriptionToList(StringBuilder messageBuilder, Book book, boolean showBookNumber, int bookNumber, boolean withPrices) {
        if (showBookNumber)
            messageBuilder.append(String.format("%d. ", bookNumber));
        messageBuilder.append(String.format("\"%s\" by %s", book.getTitle(), book.getAuthor()));
        if(withPrices)
            messageBuilder.append(String.format("; Price: %.2f", book.getPrice()));
        messageBuilder.append("\n");
    }
}
