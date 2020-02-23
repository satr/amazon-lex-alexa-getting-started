package io.github.satr.aws.lambda.bookstore.strategies;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.ObjectMother;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.strategies.intenthandler.OrderBookIntentHandlerStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class OrderBookIntentHandlerStrategyTest {
    @Mock
    LambdaLogger lambdaLogger;

    private OrderBookIntentHandlerStrategy strategy;

    @Before
    public void setUp() throws Exception {
        strategy = new OrderBookIntentHandlerStrategy();
    }
    @Test
    public void handle() {
        LexRequest request = ObjectMother.createLexRequestWithBookBookTitleAndAuthor("Some Book Title", "Some Author");

        LexRespond respond = strategy.handle(request, lambdaLogger);

        assertNotNull(respond);
        assertNotNull(respond.getDialogAction());
        assertNotNull(respond.getDialogAction().getMessage());
        assertEquals("Ordered a book \"Some Book Title\" by \"Some Author\"", respond.getDialogAction().getMessage().getContent());
    }
}