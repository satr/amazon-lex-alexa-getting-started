package io.github.satr.aws.lambda.bookstore.lambda.ask;
// Copyright © 2020, github.com/satr, MIT License

import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.model.ui.SsmlOutputSpeech;
import com.amazon.ask.util.JacksonSerializer;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.BookStoreAskLambda;
import io.github.satr.aws.lambda.bookstore.common.OperationValueResultImpl;
import io.github.satr.aws.lambda.bookstore.constants.SessionAttributeKey;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;
import io.github.satr.aws.lambda.bookstore.services.ServiceFactory;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookStoreAskLambdaSelectBookIntentTest {
    private final JacksonSerializer serializer = new JacksonSerializer();
    private final static String OUTPUT_SPEECH_TYPE = "SSML";
    private BookStoreAskLambda lambda;
    @Mock
    Context context;
    @Mock
    LambdaLogger lambdaLogger;
    @Mock
    ServiceFactory serviceFactory;
    @Mock
    BookStorageService bookStorageService;
    @Mock
    SearchBookResultService searchBookResultService;
    @Mock
    BasketService basketService;

    @Before
    public void setUp() {
        when(serviceFactory.getBookStorageService()).thenReturn(bookStorageService);
        when(serviceFactory.getSearchBookResultService()).thenReturn(searchBookResultService);
        when(serviceFactory.getBasketService()).thenReturn(basketService);
        when(context.getLogger()).thenReturn(lambdaLogger);

        lambda = new BookStoreAskLambda(serviceFactory);
    }

    @Test
    public void handleRequestReturnsRespond() throws IOException {
        InputStream inputStream = ObjectMother.createInputStreamFromJson("select-book-by-pos-in-seq-request-ask.json");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OperationValueResultImpl<Book> result = new OperationValueResultImpl<>();
        Book selectedBook = ObjectMother.getRandomBook();
        result.setValue(selectedBook);
        when(searchBookResultService.getByPositionInSequence("1st")).thenReturn(result);

        assert inputStream != null;
        lambda.handleRequest(inputStream, outputStream, null);

        String respondAsString = outputStream.toString(Charset.defaultCharset());
        assertTrue(respondAsString.length() > 0);
        System.out.println(respondAsString);
    }

    @Test
    public void handleRequestWithFullOrderBookIntentRequestHasCorrectRespond() throws IOException {
        InputStream inputStream = ObjectMother.createInputStreamFromJson("select-book-by-pos-in-seq-request-ask.json");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OperationValueResultImpl<Book> result = new OperationValueResultImpl<>();
        Book selectedBook = ObjectMother.getRandomBook();
        result.setValue(selectedBook);
        when(searchBookResultService.getByPositionInSequence("1st")).thenReturn(result);

        assert inputStream != null;
        lambda.handleRequest(inputStream, outputStream, null);

        String respondAsString = outputStream.toString(Charset.defaultCharset());
        assertTrue(respondAsString.length() > 0);
        System.out.println(respondAsString);

        ResponseEnvelope responseEnvelope = serializer.deserialize(respondAsString, ResponseEnvelope.class);
        assertEquals(OUTPUT_SPEECH_TYPE, responseEnvelope.getResponse().getOutputSpeech().getType());
        SsmlOutputSpeech outputSpeech = (SsmlOutputSpeech) responseEnvelope.getResponse().getOutputSpeech();
        assertNotNull(outputSpeech);
        assertTrue(outputSpeech.getSsml().length() > 0);
    }
/*
*  Expected Response:
*  {
     "outputSpeech":{"type":"SSML","ssml":"<speak>Title: \"Title-e292ea18-ce44-4f0d-bdb5-1dc4652beb72\"
     Author: Author-26481ed5-e52f-41b5-aa31-65022e8e4880
     Issued: 1998
       ISBN: 614017001
      Price: 32.19</speak>"
    }
}]
*/
    @Test
    public void handleRequestWithFullOrderBookIntentRequestPutsFoundBooksToBasketRepo() throws IOException {
        OperationValueResultImpl<Book> result = new OperationValueResultImpl<>();
        Book selectedBook = ObjectMother.getRandomBook();
        result.setValue(selectedBook);
        when(searchBookResultService.getByPositionInSequence("1st")).thenReturn(result);
        InputStream inputStream = ObjectMother.createInputStreamFromJson("select-book-by-pos-in-seq-request-ask.json");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        assert inputStream != null;
        lambda.handleRequest(inputStream, outputStream, null);

        ResponseEnvelope responseEnvelope = serializer.deserialize(outputStream.toString(Charset.defaultCharset()), ResponseEnvelope.class);
        String value = (String) responseEnvelope.getSessionAttributes().get(SessionAttributeKey.SelectedBookIsbn);
        assertEquals(value, selectedBook.getIsbn());
    }
}