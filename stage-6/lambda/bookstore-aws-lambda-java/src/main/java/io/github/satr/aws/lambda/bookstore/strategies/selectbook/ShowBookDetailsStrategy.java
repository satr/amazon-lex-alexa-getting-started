package io.github.satr.aws.lambda.bookstore.strategies.selectbook;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.constants.SessionAttributeKey;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.MessageFormatter;
import io.github.satr.aws.lambda.bookstore.respond.Response;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;

public class ShowBookDetailsStrategy extends AbstractSelectBookStrategy {
    public ShowBookDetailsStrategy(SearchBookResultService searchBookResultService, MessageFormatter messageFormatter) {
        super(searchBookResultService, messageFormatter);
    }

    @Override
    protected void processCustom(Response respond, Book selectedBook) {
        respond.getSessionAttributes().put(SessionAttributeKey.SelectedBookIsbn, selectedBook.getIsbn());
        respond.getDialogAction()
                .getMessage()
                .setContent(messageFormatter.getBookFullDescription(selectedBook, "\n"));
    }
}
