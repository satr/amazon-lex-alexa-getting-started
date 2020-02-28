package io.github.satr.aws.lambda.bookstore.strategies.selectbook;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookFormatter;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;

public class ShowBookDetailsStrategy extends AbstractSelectBookStrategy {
    public ShowBookDetailsStrategy(FoundBookListService foundBookListService) {
        super(foundBookListService);
    }

    @Override
    protected void processCustom(LexRespond respond, Book selectedBook) {
        respond.getDialogAction()
                .getMessage()
                .setContent(BookFormatter.getFullDescription(selectedBook, "\n"));
    }
}
