package io.github.satr.aws.lambda.bookstore.lambda;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.BookStoreLexLambda;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
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
public class BookStoreLambdaSearchBookByTitleIntentTestLex {
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
        Map<String, Object> map = ObjectMother.createMapFromJson("simple-search-book-by-title-intent-request.json");

        Object respond = lambda.handleRequest(map, context);

        assertNotNull(respond);
    }

    @Test
    public void handleRequestWithFullOrderBookIntentRequest() {
        Map<String, Object> map = ObjectMother.createMapFromJson("full-search-book-by-title-intent-request.json");

        Object respond = lambda.handleRequest(map, context);

        assertNotNull(respond);
    }

    @Test
    public void handleRequestWithFullOrderBookIntentRequestHasCorrectRespond() {
        Map<String, Object> input = ObjectMother.createMapFromJson("full-search-book-by-title-intent-request.json");

        Response response = (Response)lambda.handleRequest(input, context);

        assertNotNull(response.getDialogAction());
        assertEquals(DialogAction.FulfillmentState.Fulfilled, response.getDialogAction().getFulfillmentState());
        assertEquals(DialogAction.Type.Close, response.getDialogAction().getType());
        assertNotNull(response.getDialogAction().getMessage());
        assertEquals(Message.ContentType.PlainText, response.getDialogAction().getMessage().getContentType());
        assertNotNull(response.getDialogAction().getMessage().getContent());
        System.out.println(response.getDialogAction().getMessage().getContent());
    }
}