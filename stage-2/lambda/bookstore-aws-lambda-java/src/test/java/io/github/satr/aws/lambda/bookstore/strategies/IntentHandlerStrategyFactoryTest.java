package io.github.satr.aws.lambda.bookstore.strategies;
// Copyright © 2020, github.com/satr, MIT License

import org.junit.Test;

import static org.junit.Assert.*;

public class IntentHandlerStrategyFactoryTest {
    @Test
    public void getByOrderBookIntent() {
        IntentHandlerStrategy handlerStrategy = IntentHandlerStrategyFactory.getBy(IntentName.OrderBook);
        assertEquals(OrderBookIntentHandlerStrategy.class, handlerStrategy.getClass());
    }

    @Test
    public void getBySearchBookByTitleIntent() {
        IntentHandlerStrategy handlerStrategy = IntentHandlerStrategyFactory.getBy(IntentName.SearchBookByTitle);
        assertEquals(SearchBookByTitleIntentHandlerStrategy.class, handlerStrategy.getClass());
    }

    @Test
    public void getByNotRecognizedIntent() {
        IntentHandlerStrategy handlerStrategy = IntentHandlerStrategyFactory.getBy("Not-recognized-intent-name");
        assertEquals(NotRecognizedIntentHandler.class, handlerStrategy.getClass());
    }
}