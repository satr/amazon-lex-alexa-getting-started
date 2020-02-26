package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookListFormatter;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;
import java.util.List;

public class ShowFoundBookListIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    private final FoundBookListService foundBookListService;

    public ShowFoundBookListIntentHandlerStrategy(FoundBookListService foundBookListService) {
        this.foundBookListService = foundBookListService;
    }

    @Override
    public LexRespond handle(LexRequest request, LambdaLogger logger) {
        List<Book> bookList = foundBookListService.getList();
        String message = bookList.isEmpty()
                ? "Last search result is empty."
                : BookListFormatter.shortDescriptionList(bookList, "Last search result:\n");
        return getCloseFulfilledLexRespond(request, message);
    }
}
