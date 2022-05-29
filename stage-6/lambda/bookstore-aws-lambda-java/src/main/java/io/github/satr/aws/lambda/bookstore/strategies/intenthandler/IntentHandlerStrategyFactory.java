package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.constants.IntentName;
import io.github.satr.aws.lambda.bookstore.entity.formatter.MessageFormatter;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;

import java.util.HashMap;
import java.util.Map;

public abstract class IntentHandlerStrategyFactory {
    private Map<String, IntentHandlerStrategy> strategies = new HashMap<>();
    private IntentHandlerStrategy notRecognizedIntent = new NotRecognizedIntentHandler(null);

    protected IntentHandlerStrategyFactory(BookStorageService bookStorageService, SearchBookResultService searchBookResultService, BasketService basketService, MessageFormatter messageFormatter) {
        strategies.put(IntentName.Introduction, createIntroductionIntentHandlerStrategy());
        strategies.put(IntentName.SearchBookByTitle, new SearchBookByTitleIntentHandlerStrategy(bookStorageService, searchBookResultService, messageFormatter));
        strategies.put(IntentName.SelectBook, new SelectBookIntentHandlerStrategy(searchBookResultService, basketService, messageFormatter));
        strategies.put(IntentName.ShowBookSearchResult, new ShowSearchBookResultIntentHandlerStrategy(searchBookResultService, messageFormatter));
        strategies.put(IntentName.AddBookToBasket, new AddBookToBasketIntentHandlerStrategy(bookStorageService, basketService, messageFormatter));
        strategies.put(IntentName.RemoveBookFromBasket, new RemoveBookFromBasketIntentHandlerStrategy(searchBookResultService, basketService, messageFormatter));
        strategies.put(IntentName.ShowBasket, new ShowBasketIntentHandlerStrategy(basketService, messageFormatter));
        strategies.put(IntentName.CompleteOrder, new CompleteOrderIntentHandlerStrategy(basketService, messageFormatter));
    }

    protected abstract IntroductionIntentHandlerStrategy createIntroductionIntentHandlerStrategy();

    public IntentHandlerStrategy getBy(String intentName) {
        return strategies.getOrDefault(intentName, notRecognizedIntent);
    }
}
