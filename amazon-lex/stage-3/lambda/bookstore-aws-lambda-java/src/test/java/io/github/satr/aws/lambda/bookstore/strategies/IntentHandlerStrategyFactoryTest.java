package io.github.satr.aws.lambda.bookstore.strategies;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.constants.IntentName;
import io.github.satr.aws.lambda.bookstore.services.BookOrderService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;
import io.github.satr.aws.lambda.bookstore.strategies.intenthandler.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class IntentHandlerStrategyFactoryTest {
    @Mock
    BookStorageService bookStorageService;
    @Mock
    FoundBookListService foundBookListService;
    @Mock
    BookOrderService bookOrderService;
    private IntentHandlerStrategyFactory strategyFactory;

    @Before
    public void setUp() throws Exception {
        strategyFactory = new IntentHandlerStrategyFactory(bookStorageService, foundBookListService, bookOrderService);
    }
    @Test
    public void getByOrderBookIntent() {
        IntentHandlerStrategy handlerStrategy = strategyFactory.getBy(IntentName.OrderBook);
        assertEquals(OrderBookIntentHandlerStrategy.class, handlerStrategy.getClass());
    }

    @Test
    public void getBySearchBookByTitleIntent() {
        IntentHandlerStrategy handlerStrategy = strategyFactory.getBy(IntentName.SearchBookByTitle);
        assertEquals(SearchBookByTitleIntentHandlerStrategy.class, handlerStrategy.getClass());
    }

    @Test
    public void getByNotRecognizedIntent() {
        IntentHandlerStrategy handlerStrategy = strategyFactory.getBy("Not-recognized-intent-name");
        assertEquals(NotRecognizedIntentHandler.class, handlerStrategy.getClass());
    }
}