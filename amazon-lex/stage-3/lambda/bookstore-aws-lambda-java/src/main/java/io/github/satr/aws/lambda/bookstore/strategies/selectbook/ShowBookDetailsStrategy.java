package io.github.satr.aws.lambda.bookstore.strategies.selectbook;

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookFormatter;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;

public class ShowBookDetailsStrategy extends AbstractSelectBookStrategy {
    public ShowBookDetailsStrategy(BookStorageService bookStorageService, FoundBookListService foundBookListService) {
        super(bookStorageService, foundBookListService);
    }

    @Override
    protected void processCustom(LexRespond respond, Book selectedBook) {
        respond.getDialogAction()
                .getMessage()
                .setContent(BookFormatter.getFullDescription(selectedBook, "\n"));
    }
}
