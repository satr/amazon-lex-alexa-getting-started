package io.github.satr.aws.lambda.bookstore.alexaskillhandlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.strategies.intenthandler.IntentHandlerStrategy;
import io.github.satr.aws.lambda.bookstore.strategies.intenthandler.IntentHandlerStrategyFactory;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class GeneralRequestHandler extends AbstractRequestHandler {

    private final IntentHandlerStrategy intentHandlerStrategy;
    private final String intentName;

    public GeneralRequestHandler(IntentHandlerStrategyFactory intentHandlerStrategyFactory, String intentName, Logger logger) {
        super(logger);
        this.intentName = intentName;
        intentHandlerStrategy = intentHandlerStrategyFactory.getBy(intentName);
    }

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName(intentName));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        logInput(handlerInput);
        String message = intentHandlerStrategy.handle(convert(handlerInput), convert(logger)).getDialogAction().getMessage().getContent();
        return getResponseWithSpeech(handlerInput, message);
    }

    private LexRequest convert(HandlerInput handlerInput) {
        RequestEnvelope envelope = handlerInput.getRequestEnvelope();
        LexRequest request = new LexRequest();
        request.setSessionId(envelope.getSession().getSessionId());
        request.setSessionAttributes(envelope.getSession().getAttributes());
        request.setUserId(envelope.getSession().getUser().getUserId());
        request.setIntentSlots(new HashMap<>());//TODO - get intent slots!
        return request;
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
