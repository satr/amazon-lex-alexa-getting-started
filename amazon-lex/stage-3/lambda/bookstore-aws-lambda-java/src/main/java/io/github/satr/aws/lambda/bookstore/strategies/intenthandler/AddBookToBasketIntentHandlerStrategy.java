package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.common.OperationResult;
import io.github.satr.aws.lambda.bookstore.constants.SessionAttributeKey;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookFormatter;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;

public class AddBookToBasketIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    private final BookStorageService bookStorageService;
    private final BasketService basketService;

    public AddBookToBasketIntentHandlerStrategy(BookStorageService bookStorageService, BasketService basketService) {
        this.bookStorageService = bookStorageService;
        this.basketService = basketService;
    }

    @Override
    public LexRespond handle(LexRequest request, LambdaLogger logger) {
        String selectedBookIsbn = request.getSessionAttribute(SessionAttributeKey.SelectedBookIsbn);
        if(selectedBookIsbn == null)
            return getCloseFulfilledLexRespond(request, "Please select a book.");
        Book selectedBook = bookStorageService.getBookByIsbn(selectedBookIsbn);
        if (selectedBook == null)
            return getCloseFulfilledLexRespond(request, "Book not found by ISBN:%s", selectedBookIsbn);
        OperationResult result = basketService.add(selectedBook);
        return result.success()
            ? getCloseFulfilledLexRespond(request, "Book added to basket: %s", BookFormatter.getShortDescription(selectedBook))
            : getCloseFulfilledLexRespond(request, result.getErrorsAsString("\n"));
    }
}
