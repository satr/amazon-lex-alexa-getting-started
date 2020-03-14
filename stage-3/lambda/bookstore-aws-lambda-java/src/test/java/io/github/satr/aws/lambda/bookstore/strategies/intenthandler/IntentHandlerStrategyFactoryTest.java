package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.constants.IntentName;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;
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
    SearchBookResultService searchBookResultService;
    @Mock
    BasketService basketService;
    private IntentHandlerStrategyFactory strategyFactory;

    @Before
    public void setUp() throws Exception {
        strategyFactory = new IntentHandlerStrategyFactory(bookStorageService, searchBookResultService, basketService);
    }

    @Test
    public void getBySearchBookByTitleIntent() {
        IntentHandlerStrategy handlerStrategy = strategyFactory.getBy(IntentName.SearchBookByTitle);
        assertEquals(SearchBookByTitleIntentHandlerStrategy.class, handlerStrategy.getClass());
    }

    @Test
    public void getByShowBasketIntent() {
        IntentHandlerStrategy handlerStrategy = strategyFactory.getBy(IntentName.ShowBasket);
        assertEquals(ShowBasketIntentHandlerStrategy.class, handlerStrategy.getClass());
    }

    @Test
    public void getByAddBookToBasketIntent() {
        IntentHandlerStrategy handlerStrategy = strategyFactory.getBy(IntentName.AddBookToBasket);
        assertEquals(AddBookToBasketIntentHandlerStrategy.class, handlerStrategy.getClass());
    }

    @Test
    public void getByNotRecognizedIntent() {
        IntentHandlerStrategy handlerStrategy = strategyFactory.getBy("Not-recognized-intent-name");
        assertEquals(NotRecognizedIntentHandler.class, handlerStrategy.getClass());
    }
}