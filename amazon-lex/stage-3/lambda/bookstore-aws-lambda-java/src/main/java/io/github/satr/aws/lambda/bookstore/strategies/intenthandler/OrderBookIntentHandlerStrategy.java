package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;

public class OrderBookIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    @Override
    public LexRespond handle(LexRequest request, LambdaLogger logger) {
        return getCloseFulfilledLexRespond(request, "Ordered a book \"%s\" by \"%s\"",
                                                            request.getSlots().get(IntentSlotName.BookTitle),
                                                            request.getSlots().get(IntentSlotName.BookAuthor));
    }
}
