package io.github.satr.aws.lambda.bookstore.alexaskillhandlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import org.slf4j.Logger;

import java.util.Optional;

public class NotRecognizedIntentHandler extends AbstractRequestHandler {
    public NotRecognizedIntentHandler(Logger logger) {
        super(logger);
    }

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return true;
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        logInput(handlerInput);
        return getResponseWithSpeech(handlerInput, "Received Intent");
    }
}
