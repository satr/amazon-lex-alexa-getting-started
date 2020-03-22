package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.constants.SessionAttributeKey;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.respond.Response;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;
import io.github.satr.aws.lambda.bookstore.strategies.selectbook.RemoveBookFromBasketStrategy;

public class RemoveBookFromBasketIntentHandlerStrategy extends AbstractSelectBookIntentHandlerStrategy {

    private final RemoveBookFromBasketStrategy removeBookFromBasketStrategy;

    public RemoveBookFromBasketIntentHandlerStrategy(SearchBookResultService searchBookResultService, BasketService basketService) {
        removeBookFromBasketStrategy = new RemoveBookFromBasketStrategy(searchBookResultService, basketService);
    }

    @Override
    protected Response customHandle(Request request, Response respond, String itemNumber, String positionInSequence) {
        selectBookBy(removeBookFromBasketStrategy, itemNumber, positionInSequence, request.getSessionAttribute(SessionAttributeKey.SelectedBookIsbn))
                .processSelectedBook(respond);

        return respond;
    }}
