package io.github.satr.aws.lambda.bookstore.ask.handlers;
// Copyright © 2020, github.com/satr, MIT License

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.request.RequestFactory;
import io.github.satr.aws.lambda.bookstore.respond.Response;
import io.github.satr.aws.lambda.bookstore.strategies.intenthandler.IntentHandlerStrategy;
import io.github.satr.aws.lambda.bookstore.strategies.intenthandler.IntentHandlerStrategyFactory;
import org.slf4j.Logger;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class GeneralAskRequestHandler extends AbstractAskRequestHandler {

    private final IntentHandlerStrategy intentHandlerStrategy;
    private final String intentName;
    private boolean shouldEndSession;

    public GeneralAskRequestHandler(IntentHandlerStrategyFactory intentHandlerStrategyFactory, String intentName, Logger logger) {
        super(logger);
        this.intentName = intentName;
        intentHandlerStrategy = intentHandlerStrategyFactory.getBy(intentName);
    }

    public GeneralAskRequestHandler withShouldEndSession(boolean shouldEndSession) {
        this.shouldEndSession = shouldEndSession;
        return this;
    }

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName(intentName));
    }

    @Override
    public Optional<com.amazon.ask.model.Response> handle(HandlerInput handlerInput) {
        logInput(handlerInput);
        Request request = RequestFactory.createFromAskInput(handlerInput);
        Response strategyResponse = intentHandlerStrategy.handle(request, convert(logger));
        if(!shouldEndSession)
            transferSessionAttributes(handlerInput, strategyResponse);

        return handlerInput.getResponseBuilder()
                .withSpeech(strategyResponse.getDialogAction().getMessage().getContent())
                .withShouldEndSession(shouldEndSession)
                .build();
    }

    // https://developer.amazon.com/en-US/docs/alexa/custom-skills/manage-skill-session-and-session-attributes.html
    private void transferSessionAttributes(HandlerInput handlerInput, Response strategyResponse) {
        handlerInput.getAttributesManager().setSessionAttributes(strategyResponse.getSessionAttributes());
    }

    private LambdaLogger convert(Logger logger) {
        return new LambdaLogger() {
            @Override
            public void log(String message) {
                logger.info(message);
            }

            @Override
            public void log(byte[] message) {
            }
        };
    }
}
