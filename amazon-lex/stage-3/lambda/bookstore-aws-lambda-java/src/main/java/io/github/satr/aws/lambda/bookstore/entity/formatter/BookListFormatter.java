package io.github.satr.aws.lambda.bookstore.entity.formatter;

import io.github.satr.aws.lambda.bookstore.entity.Book;
import java.util.List;

public final class BookListFormatter {
    public static String shortDescriptionList(List<Book> books) {
        return shortDescriptionList(books, "");
    }

    public static String shortDescriptionList(List<Book> books, String headingLineFormat, Object... args) {
        StringBuilder messageBuilder = new StringBuilder(String.format(headingLineFormat, args));
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            messageBuilder.append(String.format("%d. \"%s\" by %s\n", i + 1, book.getTitle(), book.getAuthor()));
        }
        return messageBuilder.toString();
    }
}
