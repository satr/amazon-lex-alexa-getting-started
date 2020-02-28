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

    private static String getShortDescriptionList(boolean withPrices, List<Book> books, String headingLineFormat, Object... args) {
        StringBuilder messageBuilder = new StringBuilder(String.format(headingLineFormat, args));
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            messageBuilder.append(String.format("%d. \"%s\" by %s", i + 1, book.getTitle(), book.getAuthor()));
            if(withPrices)
                messageBuilder.append(String.format("; Price: %.2f", book.getPrice()));
            messageBuilder.append("\n");
        }
        return messageBuilder.toString();
    }
}
