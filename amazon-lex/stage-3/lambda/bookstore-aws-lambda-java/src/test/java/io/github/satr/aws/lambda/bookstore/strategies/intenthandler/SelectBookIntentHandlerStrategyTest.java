package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.common.OperationValueResult;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotValue;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;
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
    FoundBookListService foundBookListService;
    @Mock
    BasketService bookOrderService;
    @Mock
    OperationValueResult<Book> selectedBookResult;
    private SelectBookIntentHandlerStrategy strategy;
    private Book selectedBook;

    @Before
    public void setUp() throws Exception {
        strategy = new SelectBookIntentHandlerStrategy(bookStorageService, foundBookListService, bookOrderService);
        selectedBook = ObjectMother.getRandomBook();
        when(selectedBookResult.getValue()).thenReturn(selectedBook);
        when(selectedBookResult.success()).thenReturn(true);
        when(selectedBookResult.getErrors()).thenReturn(new LinkedList<>());
    }

    @Test
    public void handleFindShowBookWithNumber() {
        LexRequest request = ObjectMother.createLexRequestForSelectBook(IntentSlotValue.ChooseFromListAction.Show, "1", null);
        when(foundBookListService.getByNumberInSequence(1)).thenReturn(selectedBookResult);

        strategy.handle(request, logger);

        verify(foundBookListService, times(1)).getByNumberInSequence(1);
    }

    @Test
    public void handleFindShowBookWithPosition() {
        LexRequest request = ObjectMother.createLexRequestForSelectBook(IntentSlotValue.ChooseFromListAction.Show, null, IntentSlotValue.PositionInSequence.Second);
        when(foundBookListService.getByPositionInSequence("second")).thenReturn(selectedBookResult);

        strategy.handle(request, logger);

        verify(foundBookListService, times(1)).getByPositionInSequence("second");
    }

    @Test
    public void handleFindShowBookWithPositionLast() {
        LexRequest request = ObjectMother.createLexRequestForSelectBook(IntentSlotValue.ChooseFromListAction.Show, null, IntentSlotValue.PositionInSequence.Last);
        when(foundBookListService.getByPositionInSequence("last")).thenReturn(selectedBookResult);

        strategy.handle(request, logger);

        verify(foundBookListService, times(1)).getByPositionInSequence("last");
    }

    @Test
    public void handleCannotFindBookWithNumberReturnsError() {
        LexRequest request = ObjectMother.createLexRequestForSelectBook(IntentSlotValue.ChooseFromListAction.Show, "1", null);
        when(foundBookListService.getByNumberInSequence(1)).thenReturn(selectedBookResult);
        when(selectedBookResult.success()).thenReturn(false);
        when(selectedBookResult.getErrorsAsString(any(String.class))).thenReturn("Some-Error");

        LexRespond respond = strategy.handle(request, logger);

        assertEquals("Book is not selected:\nSome-Error\nPlease try again", respond.getDialogAction().getMessage().getContent());
    }

    @Test
    public void handleCannotFindBookWithPositionReturnsError() {
        LexRequest request = ObjectMother.createLexRequestForSelectBook(IntentSlotValue.ChooseFromListAction.Show, null, IntentSlotValue.PositionInSequence.Second);
        when(foundBookListService.getByPositionInSequence("second")).thenReturn(selectedBookResult);
        when(selectedBookResult.success()).thenReturn(false);
        when(selectedBookResult.getErrorsAsString(any(String.class))).thenReturn("Some-Error");

        LexRespond respond = strategy.handle(request, logger);

        assertEquals("Book is not selected:\nSome-Error\nPlease try again", respond.getDialogAction().getMessage().getContent());
    }

    @Test
    public void handleCanFindBookWithNumberReturnsBookDescription() {
        LexRequest request = ObjectMother.createLexRequestForSelectBook(IntentSlotValue.ChooseFromListAction.Show, "1", null);
        when(foundBookListService.getByNumberInSequence(1)).thenReturn(selectedBookResult);

        LexRespond respond = strategy.handle(request, logger);

        String messageContent = respond.getDialogAction().getMessage().getContent();
        assertTrue(messageContent.contains("Title: \"" + selectedBook.getTitle() + "\""));
        assertTrue(messageContent.contains("Author: " + selectedBook.getAuthor()));
        assertTrue(messageContent.contains("Issued: " + selectedBook.getIssueYear()));
        assertTrue(messageContent.contains("ISBN: " + selectedBook.getIsbn()));
    }

    @Test
    public void handleCanFindBookWithPositionReturnsBookDescription() {
        LexRequest request = ObjectMother.createLexRequestForSelectBook(IntentSlotValue.ChooseFromListAction.Show, null, IntentSlotValue.PositionInSequence.Second);
        when(foundBookListService.getByPositionInSequence("second")).thenReturn(selectedBookResult);

        LexRespond respond = strategy.handle(request, logger);

        String messageContent = respond.getDialogAction().getMessage().getContent();
        assertTrue(messageContent.contains("Title: \"" + selectedBook.getTitle() + "\""));
        assertTrue(messageContent.contains("Author: " + selectedBook.getAuthor()));
        assertTrue(messageContent.contains("Issued: " + selectedBook.getIssueYear()));
        assertTrue(messageContent.contains("ISBN: " + selectedBook.getIsbn()));
    }
}