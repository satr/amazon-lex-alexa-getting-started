package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.constants.IntentName;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;

import java.util.HashMap;
import java.util.Map;

public class IntentHandlerStrategyFactory {
    private Map<String, IntentHandlerStrategy> strategies = new HashMap<>();
    private IntentHandlerStrategy notRecognizedIntent = new NotRecognizedIntentHandler();

    public IntentHandlerStrategyFactory(BookStorageService bookStorageService, SearchBookResultService searchBookResultService, BasketService basketService) {
        strategies.put(IntentName.Introduction, new IntroductionIntentHandlerStrategy());
        strategies.put(IntentName.SearchBookByTitle, new SearchBookByTitleIntentHandlerStrategy(bookStorageService, searchBookResultService));
        strategies.put(IntentName.SelectBook, new SelectBookIntentHandlerStrategy(searchBookResultService, basketService));
        strategies.put(IntentName.ShowBookSearchResult, new ShowSearchBookResultIntentHandlerStrategy(searchBookResultService));
        strategies.put(IntentName.AddBookToBasket, new AddBookToBasketIntentHandlerStrategy(bookStorageService, basketService));
        strategies.put(IntentName.RemoveBookFromBasket, new RemoveBookFromBasketIntentHandlerStrategy(searchBookResultService, basketService));
        strategies.put(IntentName.ShowBasket, new ShowBasketIntentHandlerStrategy(basketService));
        strategies.put(IntentName.CompleteOrder, new CompleteOrderIntentHandlerStrategy(basketService));
    }

    public IntentHandlerStrategy getBy(String intentName) {
        return strategies.getOrDefault(intentName, notRecognizedIntent);
    }
}
