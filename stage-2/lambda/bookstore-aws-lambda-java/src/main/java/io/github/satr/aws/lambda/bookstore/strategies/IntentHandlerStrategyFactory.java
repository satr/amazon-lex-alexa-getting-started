package io.github.satr.aws.lambda.bookstore.strategies;
// Copyright © 2020, github.com/satr, MIT License

import java.util.HashMap;
import java.util.Map;

public final class IntentHandlerStrategyFactory {
    private static Map<String, IntentHandlerStrategy> strategies = new HashMap<>();
    private static IntentHandlerStrategy notRecognizedIntent = new NotRecognizedIntentHandler();

    static {
        strategies.put(IntentName.OrderBook, new OrderBookIntentHandlerStrategy());
        strategies.put(IntentName.SearchBookByTitle, new SearchBookByTitleIntentHandlerStrategy());
    }

    public static IntentHandlerStrategy getBy(String intentName) {
        return strategies.getOrDefault(intentName, notRecognizedIntent);
    }
}
