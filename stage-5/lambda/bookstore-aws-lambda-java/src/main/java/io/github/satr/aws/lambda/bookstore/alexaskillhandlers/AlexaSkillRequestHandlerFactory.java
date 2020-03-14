package io.github.satr.aws.lambda.bookstore.alexaskillhandlers;

import io.github.satr.aws.lambda.bookstore.services.ServiceFactory;
import io.github.satr.aws.lambda.bookstore.strategies.intenthandler.IntentHandlerStrategyFactory;
import org.slf4j.Logger;

public class AlexaSkillRequestHandlerFactory {
    private final Logger logger;
    private final IntentHandlerStrategyFactory intentHandlerStrategyFactory;

    public AlexaSkillRequestHandlerFactory(ServiceFactory serviceFactory, Logger logger) {
        intentHandlerStrategyFactory = new IntentHandlerStrategyFactory(serviceFactory.getBookStorageService(),
                                                                        serviceFactory.getSearchBookResultService(),
                                                                        serviceFactory.getBasketService());
        this.logger = logger;
    }

    public GeneralRequestHandler getRequestHandlerFor(String intentName) {
        return new GeneralRequestHandler(intentHandlerStrategyFactory, intentName, logger);
    }

    public NotRecognizedIntentHandler getNotRecognizedIntentHandler() {
        return new NotRecognizedIntentHandler(logger);
    }
}
