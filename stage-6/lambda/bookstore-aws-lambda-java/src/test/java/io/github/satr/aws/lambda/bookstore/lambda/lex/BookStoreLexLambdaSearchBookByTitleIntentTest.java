package io.github.satr.aws.lambda.bookstore.lambda.lex;
// Copyright © 2022, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.BookStoreLexLambda;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.respond.DialogAction;
import io.github.satr.aws.lambda.bookstore.respond.Response;
import io.github.satr.aws.lambda.bookstore.respond.Message;
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

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookStoreLexLambdaSearchBookByTitleIntentTest {
    private BookStoreLexLambda lambda;
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

        lambda = new BookStoreLexLambda(serviceFactory);
    }

    @Test
    public void handleRequestWithSimpleRequest() {
        Map<String, Object> map = ObjectMother.createMapFromJson("simple-search-book-by-title-intent-request-lex.json");

        Object respond = lambda.handleRequest(map, context);

        assertNotNull(respond);
    }

    @Test
    public void handleRequestWithFullOrderBookIntentRequest() {
        Map<String, Object> map = ObjectMother.createMapFromJson("full-search-book-contains-in-title-intent-request-lex.json");

        Object respond = lambda.handleRequest(map, context);

        assertNotNull(respond);
    }

    @Test
    public void handleRequestWithFullOrderBookIntentRequestHasCorrectRespond() {
        Map<String, Object> input = ObjectMother.createMapFromJson("full-search-book-contains-in-title-intent-request-lex.json");

        Response respond = (Response)lambda.handleRequest(input, context);

        assertNotNull(respond.getDialogAction());
        assertEquals(DialogAction.FulfillmentState.Fulfilled, respond.getDialogAction().getFulfillmentState());
        assertEquals(DialogAction.Type.Close, respond.getDialogAction().getType());
        assertNotNull(respond.getDialogAction().getMessage());
        assertEquals(Message.ContentType.PlainText, respond.getDialogAction().getMessage().getContentType());
        assertNotNull(respond.getDialogAction().getMessage().getContent());
        System.out.println(respond.getDialogAction().getMessage().getContent());
    }

    @Test
    public void handleRequestWithFullOrderBookStartedByTitleIntentRequestPutsFoundBooksToBasketRepo() {
        Map<String, Object> input = ObjectMother.createMapFromJson("full-search-book-started-by-title-intent-request-lex.json");
        List<Book> foundBookList = ObjectMother.getRandomBookList(3);
        when(bookStorageService.getBooksWithTitleStartingWith("Some Book Title")).thenReturn(foundBookList);

        lambda.handleRequest(input, context);

        verify(searchBookResultService).put(foundBookList);
    }

    @Test
    public void handleRequestWithFullOrderBookContainsInTitleIntentRequestPutsFoundBooksToBasketRepo() {
        Map<String, Object> input = ObjectMother.createMapFromJson("full-search-book-contains-in-title-intent-request-lex.json");
        List<Book> foundBookList = ObjectMother.getRandomBookList(3);
        when(bookStorageService.getBooksWithTitleContaining("Some Book Title")).thenReturn(foundBookList);

        lambda.handleRequest(input, context);

        verify(searchBookResultService).put(foundBookList);
    }

    @Test
    public void handleRequestWithFullOrderBookEndsByTitleIntentRequestPutsFoundBooksToBasketRepo() {
        Map<String, Object> input = ObjectMother.createMapFromJson("full-search-book-ends-by-title-intent-request-lex.json");
        List<Book> foundBookList = ObjectMother.getRandomBookList(3);
        when(bookStorageService.getBooksWithTitleEndingWith("Some Book Title")).thenReturn(foundBookList);

        lambda.handleRequest(input, context);

        verify(searchBookResultService).put(foundBookList);
    }
}