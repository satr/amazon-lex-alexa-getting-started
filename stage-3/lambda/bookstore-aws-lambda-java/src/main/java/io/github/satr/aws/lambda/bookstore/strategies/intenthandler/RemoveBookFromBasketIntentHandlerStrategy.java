package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.response.Response;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;
import io.github.satr.aws.lambda.bookstore.strategies.selectbook.RemoveBookFromBasketStrategy;

public class RemoveBookFromBasketIntentHandlerStrategy extends AbstractSelectBookIntentHandlerStrategy {

    private final RemoveBookFromBasketStrategy removeBookFromBasketStrategy;

    public RemoveBookFromBasketIntentHandlerStrategy(SearchBookResultService searchBookResultService, BasketService basketService) {
        removeBookFromBasketStrategy = new RemoveBookFromBasketStrategy(searchBookResultService, basketService);
    }

    @Override
    protected Response customHandle(Request request, Response response, String itemNumber, String positionInSequence) {
        selectBookBy(removeBookFromBasketStrategy, itemNumber, positionInSequence)
                .processSelectedBook(response);

        return response;
    }}
