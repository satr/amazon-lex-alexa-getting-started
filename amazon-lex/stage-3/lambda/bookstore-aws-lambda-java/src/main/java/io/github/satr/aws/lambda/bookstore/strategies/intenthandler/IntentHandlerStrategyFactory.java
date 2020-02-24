package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.constants.IntentName;
import io.github.satr.aws.lambda.bookstore.services.BookOrderService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;

import java.util.HashMap;
import java.util.Map;

public class IntentHandlerStrategyFactory {
    private Map<String, IntentHandlerStrategy> strategies = new HashMap<>();
    private IntentHandlerStrategy notRecognizedIntent = new NotRecognizedIntentHandler();

    public IntentHandlerStrategyFactory(BookStorageService bookStorageService, FoundBookListService foundBookListService, BookOrderService bookOrderService) {
        strategies.put(IntentName.OrderBook, new OrderBookIntentHandlerStrategy());
        strategies.put(IntentName.SearchBookByTitle, new SearchBookByTitleIntentHandlerStrategy(bookStorageService, foundBookListService));
        strategies.put(IntentName.SelectBook, new SelectBookIntentHandlerStrategy(bookStorageService, foundBookListService, bookOrderService));
    }

    public IntentHandlerStrategy getBy(String intentName) {
        return strategies.getOrDefault(intentName, notRecognizedIntent);
    }
}
