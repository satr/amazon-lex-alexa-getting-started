package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2022, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.common.OperationResult;
import io.github.satr.aws.lambda.bookstore.constants.SessionAttributeKey;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.MessageFormatter;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.respond.Response;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;

public class AddBookToBasketIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    private final BookStorageService bookStorageService;
    private final BasketService basketService;

    public AddBookToBasketIntentHandlerStrategy(BookStorageService bookStorageService, BasketService basketService, MessageFormatter messageFormatter) {
        super(messageFormatter);
        this.bookStorageService = bookStorageService;
        this.basketService = basketService;
    }

    @Override
    public Response handle(Request request, LambdaLogger logger) {
        String selectedBookIsbn = request.getSessionAttribute(SessionAttributeKey.SelectedBookIsbn);
        if(selectedBookIsbn == null) {
            return getCloseFulfilledLexRespond(request, "Please select a book.");
        }
        Book selectedBook = bookStorageService.getBookByIsbn(selectedBookIsbn);
        if (selectedBook == null)
            return getCloseFulfilledLexRespond(request, "Book not found by ISBN:%s", selectedBookIsbn);
        OperationResult result = basketService.add(selectedBook);
        return result.success()
            ? getCloseFulfilledLexRespond(request, "Book added to basket: %s", messageFormatter.getBookShortDescription(selectedBook))
            : getCloseFulfilledLexRespond(request, result.getErrorsAsString("\n"));
    }
}
