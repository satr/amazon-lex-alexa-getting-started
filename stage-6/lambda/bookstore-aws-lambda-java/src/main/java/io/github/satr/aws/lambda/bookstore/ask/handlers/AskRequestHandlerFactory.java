package io.github.satr.aws.lambda.bookstore.ask.handlers;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.services.ServiceFactory;
import io.github.satr.aws.lambda.bookstore.strategies.intenthandler.AskIntentHandlerStrategyFactory;
import io.github.satr.aws.lambda.bookstore.strategies.intenthandler.IntentHandlerStrategyFactory;
import org.slf4j.Logger;

public class AskRequestHandlerFactory {
    private final Logger logger;
    private final IntentHandlerStrategyFactory intentHandlerStrategyFactory;

    public AskRequestHandlerFactory(ServiceFactory serviceFactory, Logger logger) {
        intentHandlerStrategyFactory = new AskIntentHandlerStrategyFactory(serviceFactory.getBookStorageService(),
                                                                        serviceFactory.getSearchBookResultService(),
                                                                        serviceFactory.getBasketService());
        this.logger = logger;
    }

    public GeneralAskRequestHandler getRequestHandlerFor(String intentName) {
        return new GeneralAskRequestHandler(intentHandlerStrategyFactory, intentName, logger);
    }

    public NotRecognizedAskIntentHandler getNotRecognizedIntentHandler() {
        return new NotRecognizedAskIntentHandler(logger);
    }
}
