package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.constants.IntentName;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;

import java.util.HashMap;
import java.util.Map;

public class IntentHandlerStrategyFactory {
    private Map<String, IntentHandlerStrategy> strategies = new HashMap<>();
    private IntentHandlerStrategy notRecognizedIntent = new NotRecognizedIntentHandler();

    public IntentHandlerStrategyFactory(BookStorageService bookStorageService, FoundBookListService foundBookListService, BasketService basketService) {
        strategies.put(IntentName.OrderBook, new OrderBookIntentHandlerStrategy());
        strategies.put(IntentName.SearchBookByTitle, new SearchBookByTitleIntentHandlerStrategy(bookStorageService, foundBookListService));
        strategies.put(IntentName.SelectBook, new SelectBookIntentHandlerStrategy(bookStorageService, foundBookListService, basketService));
        strategies.put(IntentName.ShowFoundBookList, new ShowFoundBookListIntentHandlerStrategy(foundBookListService));
        strategies.put(IntentName.AddBookToBasket, new AddBookToBasketIntentHandlerStrategy(bookStorageService, basketService));
        strategies.put(IntentName.ShowBasket, new ShowBasketIntentHandlerStrategy(basketService));
        strategies.put(IntentName.Introduction, new IntroductionIntentHandlerStrategy());
    }

    public IntentHandlerStrategy getBy(String intentName) {
        return strategies.getOrDefault(intentName, notRecognizedIntent);
    }
}
