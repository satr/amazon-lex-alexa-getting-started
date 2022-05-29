package io.github.satr.aws.lambda.bookstore.lambda.ask;
// Copyright © 2022, github.com/satr, MIT License

import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.model.ui.SsmlOutputSpeech;
import com.amazon.ask.util.JacksonSerializer;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.BookStoreAskLambda;
import io.github.satr.aws.lambda.bookstore.common.OperationValueResultImpl;
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
public class BookStoreAskLambdaAddBookToBasketIntentTest {
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
    private Book bookWithIsbnInSession;

    @Before
    public void setUp() throws Exception {
        when(serviceFactory.getBookStorageService()).thenReturn(bookStorageService);
        when(serviceFactory.getSearchBookResultService()).thenReturn(searchBookResultService);
        when(serviceFactory.getBasketService()).thenReturn(basketService);
        when(context.getLogger()).thenReturn(lambdaLogger);

        lambda = new BookStoreAskLambda(serviceFactory);

        List<Book> foundBookList = ObjectMother.getRandomBookList(3);
        final String selectedBookIsbnInSession = "1412050809";
        bookWithIsbnInSession = foundBookList.get(1);
        bookWithIsbnInSession.setIsbn(selectedBookIsbnInSession);
        OperationValueResultImpl<Book> result = new OperationValueResultImpl<>();
        result.setValue(bookWithIsbnInSession);
        when(searchBookResultService.getByIsbn(selectedBookIsbnInSession)).thenReturn(result);
    }

    @Test
    public void handleRequestToAddBookToBasketBySelecteIsbnInSessionReturnsRespond() throws IOException {
        InputStream inputStream = ObjectMother.createInputStreamFromJson("add-book-to-basket-ask.json");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        lambda.handleRequest(inputStream, outputStream, null);

        String respondAsString = outputStream.toString();
        assertTrue(respondAsString.length() > 0);
        System.out.println(respondAsString);
    }

    @Test
    public void handleRequestToAddBookToBasketBySelecteIsbnInSessionHasCorrectRespond() throws IOException {
        InputStream inputStream = ObjectMother.createInputStreamFromJson("add-book-to-basket-ask.json");
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

    @Test
    public void handleRequestToAddBookToBasketBySelecteIsbnInSessionAddsBookToBasketService() throws IOException {
        InputStream inputStream = ObjectMother.createInputStreamFromJson("add-book-to-basket-ask.json");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        lambda.handleRequest(inputStream, outputStream, null);

        verify(basketService).add(bookWithIsbnInSession);
    }
}