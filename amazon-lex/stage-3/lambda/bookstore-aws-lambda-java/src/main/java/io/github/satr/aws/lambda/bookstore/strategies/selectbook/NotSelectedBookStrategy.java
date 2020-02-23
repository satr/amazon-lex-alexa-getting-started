package io.github.satr.aws.lambda.bookstore.strategies.selectbook;

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;

public class NotSelectedBookStrategy extends AbstractSelectBookStrategy {
    public NotSelectedBookStrategy(BookStorageService bookStorageService, FoundBookListService foundBookListService) {
        super(bookStorageService, foundBookListService);
    }

    @Override
    protected void processCustom(LexRespond respond, Book selectedBook) {
        //TODO
    }
}
