package io.github.satr.aws.lambda.bookstore;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookStoreLambdaTest {
    private BookStoreLambda lambda;
    @Mock
    Context context;
    @Mock
    LambdaLogger lambdaLogger;

    @Before
    public void setUp() throws Exception {
        lambda = new BookStoreLambda();
        when(context.getLogger()).thenReturn(lambdaLogger);
    }

    @Test
    public void handleRequestWithSimpleRequest() {
        Map<String, Object> map = ObjectMother.createMapFromJson("simple-request.json");
        Object respond = lambda.handleRequest(map, context);
        Assert.assertNotNull(respond);
    }
}