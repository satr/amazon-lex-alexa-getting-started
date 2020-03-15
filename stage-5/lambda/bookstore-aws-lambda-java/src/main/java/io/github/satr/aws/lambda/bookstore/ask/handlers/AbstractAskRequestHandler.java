package io.github.satr.aws.lambda.bookstore.ask.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import org.slf4j.Logger;

import java.util.Optional;

public abstract class AbstractAskRequestHandler implements RequestHandler {
    protected final Logger logger;

    public AbstractAskRequestHandler(Logger logger) {
        this.logger = logger;
    }

    protected Optional<Response> getResponseWithSpeech(HandlerInput handlerInput, String messageFormat, Object... args) {
        return handlerInput.getResponseBuilder()
                .withSpeech(String.format(messageFormat, args))
                .build();
    }

    protected void logInput(HandlerInput handlerInput) {
        logger.debug(handlerInput.getRequestEnvelopeJson().asText());
    }
}
