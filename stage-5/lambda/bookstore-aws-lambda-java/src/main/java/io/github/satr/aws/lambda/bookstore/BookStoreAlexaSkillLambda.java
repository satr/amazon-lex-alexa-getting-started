package io.github.satr.aws.lambda.bookstore;
// Copyright © 2020, github.com/satr, MIT License

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.amazonaws.regions.Regions;
import io.github.satr.aws.lambda.bookstore.alexaskillhandlers.AlexaSkillRequestHandlerFactory;
import io.github.satr.aws.lambda.bookstore.constants.IntentName;
import io.github.satr.aws.lambda.bookstore.repositories.database.DatabaseRepositoryFactoryImpl;
import io.github.satr.aws.lambda.bookstore.services.ServiceFactory;
import io.github.satr.aws.lambda.bookstore.services.ServiceFactoryImpl;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

public class BookStoreAlexaSkillLambda extends SkillStreamHandler {
    private static Logger logger = getLogger(BookStoreAlexaSkillLambda.class);
    private static AlexaSkillRequestHandlerFactory requestHandlerFactory;

    public BookStoreAlexaSkillLambda() {
        super(getSkill(new ServiceFactoryImpl(new DatabaseRepositoryFactoryImpl(getAwsRegionForDynamoDb()))));
    }

    public BookStoreAlexaSkillLambda(ServiceFactory serviceFactory) {
        super(getSkill(serviceFactory));
    }

    private static Skill getSkill(ServiceFactory serviceFactory) {
        requestHandlerFactory = new AlexaSkillRequestHandlerFactory(serviceFactory, logger);
        return Skills.standard()
                .addRequestHandler(requestHandlerFactory.getRequestHandlerFor(IntentName.Introduction))
                .addRequestHandler(requestHandlerFactory.getRequestHandlerFor(IntentName.SearchBookByTitle))
                .addRequestHandler(requestHandlerFactory.getRequestHandlerFor(IntentName.ShowBookSearchResult))
                .addRequestHandler(requestHandlerFactory.getRequestHandlerFor(IntentName.SelectBook))
                .addRequestHandler(requestHandlerFactory.getRequestHandlerFor(IntentName.AddBookToBasket))
                .addRequestHandler(requestHandlerFactory.getRequestHandlerFor(IntentName.ShowBasket))
                .addRequestHandler(requestHandlerFactory.getRequestHandlerFor(IntentName.RemoveBookFromBasket))
                .addRequestHandler(requestHandlerFactory.getRequestHandlerFor(IntentName.CompleteOrder))
                .addRequestHandler(requestHandlerFactory.getNotRecognizedIntentHandler())
                .build();
    }

    private static Regions getAwsRegionForDynamoDb() {
        return Regions.fromName(System.getenv("AWS_REGION"));
    }
}
