package io.github.satr.aws.lambda.bookstore.lambda;
// Copyright © 2020, github.com/satr, MIT License

import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.model.ui.SsmlOutputSpeech;
import com.amazon.ask.util.JacksonSerializer;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.BookStoreAskLambda;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;
import io.github.satr.aws.lambda.bookstore.services.ServiceFactory;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookStoreAskLambdaSearchBookByTitleIntentTest {
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
    public void setUp() throws Exception {
        when(serviceFactory.getBookStorageService()).thenReturn(bookStorageService);
        when(serviceFactory.getSearchBookResultService()).thenReturn(searchBookResultService);
        when(serviceFactory.getBasketService()).thenReturn(basketService);
        when(context.getLogger()).thenReturn(lambdaLogger);

        lambda = new BookStoreAskLambda(serviceFactory);
    }

    @Test
    public void handleRequestReturnsRespond() throws IOException {
        InputStream inputStream = ObjectMother.createInputStreamFromJson("full-search-book-by-title-intent-request-alexa-skill.json");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        lambda.handleRequest(inputStream, outputStream, null);

        String respondAsString = outputStream.toString();
        assertTrue(respondAsString.length() > 0);
        System.out.println(respondAsString);
    }

    @Test
    public void handleRequestWithFullOrderBookIntentRequestHasCorrectRespond() throws IOException {
        InputStream inputStream = ObjectMother.createInputStreamFromJson("full-search-book-by-title-intent-request-alexa-skill.json");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        lambda.handleRequest(inputStream, outputStream, null);

        String respondAsString = outputStream.toString();
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
*  Optional[class Response {
    outputSpeech: class SsmlOutputSpeech {
        class OutputSpeech {
            type: SSML
            playBehavior: null
        }
        ssml: <speak>Searched books "contains" in title "Some Book Title"
        Found 3 books:
        1. "Some Book Title ABC" by Author-97ed66e3-85f3-4d01-b6d9-19bdb16ecb1b
        2. "ABC Some Book Title" by Author-b6243adc-93aa-41ba-88f0-2271d92fe83a
        3. "ABC Some Book Title XYZ" by Author-f9ffe6b4-9d30-4981-9b25-52a9adff4976</speak>
    }
    card: null
    reprompt: null
    directives: []
    shouldEndSession: null
    canFulfillIntent: null
}]
*/
    @Test
    public void handleRequestWithFullOrderBookIntentRequestPutsFoundBooksToBasketRepo() throws IOException {
        List<Book> foundBookList = ObjectMother.getRandomBookList(3);
        foundBookList.get(0).setTitle("Some Book Title ABC");
        foundBookList.get(1).setTitle("ABC Some Book Title");
        foundBookList.get(2).setTitle("ABC Some Book Title XYZ");
        when(bookStorageService.getBooksWithTitleContaining("Some Book Title")).thenReturn(foundBookList);
        InputStream inputStream = ObjectMother.createInputStreamFromJson("full-search-book-by-title-intent-request-alexa-skill.json");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        lambda.handleRequest(inputStream, outputStream, null);

        verify(searchBookResultService).put(foundBookList);
    }
}