package io.github.satr.aws.lambda.bookstore.strategies;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.response.Response;

public class NotRecognizedIntentHandler extends AbstractIntentHandlerStrategy {
    @Override
    public Response handle(LexRequest request, LambdaLogger logger) {
        return getCloseFulfilledLexRespond("Received Intent: %s", request.getIntentName());
    }
}
