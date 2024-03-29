package io.github.satr.aws.lambda.bookstore;
// Copyright © 2022, github.com/satr, MIT License

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.github.satr.aws.lambda.bookstore.repositories.database.DatabaseRepositoryFactoryImpl;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.request.RequestFactory;
import io.github.satr.aws.lambda.bookstore.services.*;
import io.github.satr.aws.lambda.bookstore.strategies.intenthandler.IntentHandlerStrategy;
import io.github.satr.aws.lambda.bookstore.strategies.intenthandler.IntentHandlerStrategyFactory;
import io.github.satr.aws.lambda.bookstore.strategies.intenthandler.LexIntentHandlerStrategyFactory;

import java.util.Map;

//Lambda with POJO as a respond
public class BookStoreLexLambda implements RequestHandler<Map<String, Object>, Object>  {
    private IntentHandlerStrategyFactory intentHandlerStrategyFactory;

    public BookStoreLexLambda() {
        this(new ServiceFactoryImpl(new DatabaseRepositoryFactoryImpl(getAwsRegionForDynamoDb())));
    }

    public BookStoreLexLambda(ServiceFactory serviceFactory) {
        intentHandlerStrategyFactory = new LexIntentHandlerStrategyFactory(serviceFactory.getBookStorageService(),
                                                                        serviceFactory.getSearchBookResultService(),
                                                                        serviceFactory.getBasketService());
    }

    @Override
    public Object handleRequest(Map<String, Object> input, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log(input.toString());//Just to show the input in the CloudWatch log
        Request request = RequestFactory.createFromLexInput(input);
        logInputProperties(logger, request);

        //Find intent-handle strategy by IntentName
        IntentHandlerStrategy intentHandlerStrategy = intentHandlerStrategyFactory.getBy(request.getIntentName());

        //Handle the request for the intent
        return intentHandlerStrategy.handle(request, logger);
    }

    private static Regions getAwsRegionForDynamoDb() {
        return Regions.fromName(System.getenv("AWS_REGION"));
    }

    private void logInputProperties(LambdaLogger logger, Request request) {
        logger.log("UserId:" + request.getUserId());
        logger.log("Bot name:" + request.getBotName());
        logger.log("Current intent name:" + request.getIntentName());

        Map<String, Object> slots = request.getSlots();
        logger.log(slots.keySet().isEmpty() ? "No Slots" : "Slots:");
        for (String slotName : slots.keySet())
            logger.log(" - " + slotName + ":" + slots.get(slotName));

        Map<String, Object> sessionAttributes = request.getSessionAttributes();
        logger.log(sessionAttributes.keySet().isEmpty() ? "No Session Attributes" : "Session Attributes:");
        for (String attr : sessionAttributes.keySet())
            logger.log(" - " + attr + ":" + sessionAttributes.get(attr));
    }
}
