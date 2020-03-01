package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;

public class NotRecognizedIntentHandler extends AbstractIntentHandlerStrategy {
    @Override
    public LexRespond handle(LexRequest request, LambdaLogger logger) {
        return getCloseFulfilledLexRespond(request, "Received Intent: %s", request.getIntentName());
    }
}
