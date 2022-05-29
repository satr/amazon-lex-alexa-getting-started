package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2022, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.common.OperationValueResult;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotValue;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.LexMessageFormatter;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.respond.Response;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SelectBookIntentHandlerStrategyTest {
    @Mock
    LambdaLogger logger;
    @Mock
    BookStorageService bookStorageService;
    @Mock
    SearchBookResultService searchBookResultService;
    @Mock
    BasketService bookOrderService;
    @Mock
    OperationValueResult<Book> selectedBookResult;
    private SelectBookIntentHandlerStrategy strategy;
    private Book selectedBook;

    @Before
    public void setUp() throws Exception {
        strategy = new SelectBookIntentHandlerStrategy(searchBookResultService, bookOrderService, new LexMessageFormatter());
        selectedBook = ObjectMother.getRandomBook();
        when(selectedBookResult.getValue()).thenReturn(selectedBook);
        when(selectedBookResult.success()).thenReturn(true);
        when(selectedBookResult.getErrors()).thenReturn(new LinkedList<>());
    }

    @Test
    public void handleFindShowBookWithNumber() {
        Request request = ObjectMother.createLexRequestForSelectBook(IntentSlotValue.ChooseFromListAction.Show, "1", null);
        when(searchBookResultService.getByNumberInSequence(1)).thenReturn(selectedBookResult);

        strategy.handle(request, logger);

        verify(searchBookResultService, times(1)).getByNumberInSequence(1);
    }

    @Test
    public void handleFindShowBookWithPosition() {
        Request request = ObjectMother.createLexRequestForSelectBook(IntentSlotValue.ChooseFromListAction.Show, null, IntentSlotValue.PositionInSequence.Second);
        when(searchBookResultService.getByPositionInSequence("second")).thenReturn(selectedBookResult);

        strategy.handle(request, logger);

        verify(searchBookResultService, times(1)).getByPositionInSequence("second");
    }

    @Test
    public void handleFindShowBookWithPositionLast() {
        Request request = ObjectMother.createLexRequestForSelectBook(IntentSlotValue.ChooseFromListAction.Show, null, IntentSlotValue.PositionInSequence.Last);
        when(searchBookResultService.getByPositionInSequence("last")).thenReturn(selectedBookResult);

        strategy.handle(request, logger);

        verify(searchBookResultService, times(1)).getByPositionInSequence("last");
    }

    @Test
    public void handleCannotFindBookWithNumberReturnsError() {
        Request request = ObjectMother.createLexRequestForSelectBook(IntentSlotValue.ChooseFromListAction.Show, "1", null);
        when(searchBookResultService.getByNumberInSequence(1)).thenReturn(selectedBookResult);
        when(selectedBookResult.success()).thenReturn(false);
        when(selectedBookResult.getErrorsAsString(any(String.class))).thenReturn("Some-Error");

        Response respond = strategy.handle(request, logger);

        assertEquals("Book is not selected:\nSome-Error\nPlease try again", respond.getDialogAction().getMessage().getContent());
    }

    @Test
    public void handleCannotFindBookWithPositionReturnsError() {
        Request request = ObjectMother.createLexRequestForSelectBook(IntentSlotValue.ChooseFromListAction.Show, null, IntentSlotValue.PositionInSequence.Second);
        when(searchBookResultService.getByPositionInSequence("second")).thenReturn(selectedBookResult);
        when(selectedBookResult.success()).thenReturn(false);
        when(selectedBookResult.getErrorsAsString(any(String.class))).thenReturn("Some-Error");

        Response respond = strategy.handle(request, logger);

        assertEquals("Book is not selected:\nSome-Error\nPlease try again", respond.getDialogAction().getMessage().getContent());
    }

    @Test
    public void handleCanFindBookWithNumberReturnsBookDescription() {
        Request request = ObjectMother.createLexRequestForSelectBook(IntentSlotValue.ChooseFromListAction.Show, "1", null);
        when(searchBookResultService.getByNumberInSequence(1)).thenReturn(selectedBookResult);

        Response respond = strategy.handle(request, logger);

        String messageContent = respond.getDialogAction().getMessage().getContent();
        assertTrue(messageContent.contains("Title: \"" + selectedBook.getTitle() + "\""));
        assertTrue(messageContent.contains("Author: " + selectedBook.getAuthor()));
        assertTrue(messageContent.contains("Issued: " + selectedBook.getIssueYear()));
        assertTrue(messageContent.contains("ISBN: " + selectedBook.getIsbn()));
    }

    @Test
    public void handleCanFindBookWithPositionReturnsBookDescription() {
        Request request = ObjectMother.createLexRequestForSelectBook(IntentSlotValue.ChooseFromListAction.Show, null, IntentSlotValue.PositionInSequence.Second);
        when(searchBookResultService.getByPositionInSequence("second")).thenReturn(selectedBookResult);

        Response respond = strategy.handle(request, logger);

        String messageContent = respond.getDialogAction().getMessage().getContent();
        assertTrue(messageContent.contains("Title: \"" + selectedBook.getTitle() + "\""));
        assertTrue(messageContent.contains("Author: " + selectedBook.getAuthor()));
        assertTrue(messageContent.contains("Issued: " + selectedBook.getIssueYear()));
        assertTrue(messageContent.contains("ISBN: " + selectedBook.getIsbn()));
    }
}