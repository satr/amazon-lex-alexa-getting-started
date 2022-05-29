package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2022, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.entity.formatter.MessageFormatter;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.respond.Response;

public class NotRecognizedIntentHandler extends AbstractIntentHandlerStrategy {
    public NotRecognizedIntentHandler(MessageFormatter messageFormatter) {
        super(null);
    }

    @Override
    public Response handle(Request request, LambdaLogger logger) {
        return getCloseFulfilledLexRespond(request, "Received Intent: %s", request.getIntentName());
    }
}
