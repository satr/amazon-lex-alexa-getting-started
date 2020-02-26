package io.github.satr.aws.lambda.bookstore.services;

import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import io.github.satr.aws.lambda.bookstore.common.OperationValueResult;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotValue;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FoundBookListServiceImplTest {

    private FoundBookListServiceImpl service;

    @Before
    public void setUp() throws Exception {
        service = new FoundBookListServiceImpl();
    }

    @Test
    public void getByPositionInSequenceFirst() {
        List<Book> books = ObjectMother.getRandomBookList(3);
        service.put(books);

        OperationValueResult<Book> result = service.getByPositionInSequence(IntentSlotValue.PositionInSequence.First);

        assertTrue(result.success());
        assertNotNull(result.getValue());
        assertEquals(books.get(0), result.getValue());
    }

    @Test
    public void getByPositionInSequenceLast() {
        List<Book> books = ObjectMother.getRandomBookList(3);
        service.put(books);

        OperationValueResult<Book> result = service.getByPositionInSequence(IntentSlotValue.PositionInSequence.Last);

        assertTrue(result.success());
        assertNotNull(result.getValue());
        assertEquals(books.get(2), result.getValue());
    }

    @Test
    public void getByNotExistingPositionInSequenceSeventh() {
        List<Book> books = ObjectMother.getRandomBookList(3);
        service.put(books);

        OperationValueResult<Book> result = service.getByPositionInSequence(IntentSlotValue.PositionInSequence.Seventh);

        assertFalse(result.success());
        assertNull(result.getValue());
        assertEquals("Cannot identify a book in the list by item number 7.", result.getErrorsAsString("\n"));
    }

    @Test
    public void getByPositionInSequenceSecond() {
        List<Book> books = ObjectMother.getRandomBookList(3);
        service.put(books);

        OperationValueResult<Book> result = service.getByPositionInSequence(IntentSlotValue.PositionInSequence.Second);

        assertTrue(result.success());
        assertNotNull(result.getValue());
        assertEquals(books.get(1), result.getValue());
    }

    @Test
    public void getByNumberInSequenceOne() {
        List<Book> books = ObjectMother.getRandomBookList(3);
        service.put(books);

        OperationValueResult<Book> result = service.getByNumberInSequence(1);

        assertTrue(result.success());
        assertNotNull(result.getValue());
        assertEquals(books.get(0), result.getValue());
    }
}