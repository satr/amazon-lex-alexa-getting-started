package io.github.satr.aws.lambda.bookstore.strategies.selectbook;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookFormatter;
import io.github.satr.aws.lambda.bookstore.respond.Response;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;

public class ShowBookDetailsStrategy extends AbstractSelectBookStrategy {
    public ShowBookDetailsStrategy(SearchBookResultService searchBookResultService) {
        super(searchBookResultService);
    }

    @Override
    protected void processCustom(Response respond, Book selectedBook) {
        respond.getDialogAction()
                .getMessage()
                .setContent(BookFormatter.getFullDescription(selectedBook, "\n"));
    }
}
