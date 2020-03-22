package io.github.satr.aws.lambda.bookstore;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.response.DialogAction;
import io.github.satr.aws.lambda.bookstore.response.Response;
import io.github.satr.aws.lambda.bookstore.response.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookStoreLambdaOrderBookIntentTestLex {
    private BookStoreLexLambda lambda;
    @Mock
    Context context;
    @Mock
    LambdaLogger lambdaLogger;

    @Before
    public void setUp() throws Exception {
        lambda = new BookStoreLexLambda();
        when(context.getLogger()).thenReturn(lambdaLogger);
    }

    @Test
    public void handleRequestWithSimpleRequest() {
        Map<String, Object> input = ObjectMother.createMapFromJson("simple-order-book-intent-request.json");
        Object respond = lambda.handleRequest(input, context);
        assertNotNull(respond);
    }

    @Test
    public void handleRequestWithFullOrderBookIntentRequest() {
        Map<String, Object> input = ObjectMother.createMapFromJson("full-order-book-intent-request.json");
        Object respond = lambda.handleRequest(input, context);
        assertNotNull(respond);
    }

    @Test
    public void handleRequestWithFullOrderBookIntentRequestHasCorrectRespond() {
        Map<String, Object> input = ObjectMother.createMapFromJson("full-order-book-intent-request.json");
        Response respond = (Response)lambda.handleRequest(input, context);
        assertNotNull(respond.getDialogAction());
        assertEquals(DialogAction.FulfillmentState.Fulfilled, respond.getDialogAction().getFulfillmentState());
        assertEquals(DialogAction.Type.Close, respond.getDialogAction().getType());
        assertNotNull(respond.getDialogAction().getMessage());
        assertEquals(Message.ContentType.PlainText, respond.getDialogAction().getMessage().getContentType());
        assertNotNull(respond.getDialogAction().getMessage().getContent());
    }
}