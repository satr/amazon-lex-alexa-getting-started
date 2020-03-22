package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.constants.SessionAttributeKey;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookListFormatter;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.respond.Response;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;
import java.util.List;

public class ShowSearchBookResultIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    private final SearchBookResultService searchBookResultService;

    public ShowSearchBookResultIntentHandlerStrategy(SearchBookResultService searchBookResultService) {
        this.searchBookResultService = searchBookResultService;
    }

    @Override
    public Response handle(Request request, LambdaLogger logger) {
        List<Book> bookList = searchBookResultService.getResult();
        String message = bookList.isEmpty()
                ? "Last search result is empty."
                : BookListFormatter.getShortDescriptionList(bookList, "Last search result:\n");
        if(bookList.size() == 1)
            request.getSessionAttributes().put(SessionAttributeKey.SelectedBookIsbn, bookList.get(0).getIsbn());
        return getCloseFulfilledLexRespond(request, message);
    }
}
