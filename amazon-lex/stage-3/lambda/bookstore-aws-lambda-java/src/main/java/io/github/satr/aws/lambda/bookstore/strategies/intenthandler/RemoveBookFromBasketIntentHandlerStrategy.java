package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;
import io.github.satr.aws.lambda.bookstore.strategies.selectbook.RemoveBookFromBasketStrategy;

public class RemoveBookFromBasketIntentHandlerStrategy extends AbstractSelectBookIntentHandlerStrategy {

    private final RemoveBookFromBasketStrategy removeBookFromBasketStrategy;

    public RemoveBookFromBasketIntentHandlerStrategy(FoundBookListService foundBookListService, BasketService basketService) {
        removeBookFromBasketStrategy = new RemoveBookFromBasketStrategy(foundBookListService, basketService);
    }

    @Override
    protected LexRespond customHandle(LexRequest request, LexRespond respond, String itemNumber, String positionInSequence) {
        selectBookBy(removeBookFromBasketStrategy, itemNumber, positionInSequence)
                .processSelectedBook(respond);

        return respond;
    }}
