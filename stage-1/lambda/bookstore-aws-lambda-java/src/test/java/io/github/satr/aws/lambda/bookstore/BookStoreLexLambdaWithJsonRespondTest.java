package io.github.satr.aws.lambda.bookstore;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookStoreLexLambdaWithJsonRespondTest {
    private BookStoreLexLambdaWithJsonResponse lambda;
    @Mock
    Context context;
    @Mock
    LambdaLogger lambdaLogger;

    @Before
    public void setUp() throws Exception {
        lambda = new BookStoreLexLambdaWithJsonResponse();
        when(context.getLogger()).thenReturn(lambdaLogger);
    }

    @Test
    public void handleRequestWithSimpleRequest() {
        Map<String, Object> map = ObjectMother.createMapFromJson("simple-request.json");
        Object respond = lambda.handleRequest(map, context);
        Assert.assertNotNull(respond);
    }

    @Test
    public void handleRequestWithFullOrderBookIntentRequest() {
        Map<String, Object> map = ObjectMother.createMapFromJson("full-order-book-intent-request.json");
        Object respond = lambda.handleRequest(map, context);
        Assert.assertNotNull(respond);
    }
}