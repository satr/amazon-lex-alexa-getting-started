package io.github.satr.aws.lambda.bookstore.services;

import io.github.satr.aws.lambda.bookstore.ObjectMother;
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
    public void getByNumberInSequenceOne() {
        List<Book> books = ObjectMother.getRandomBookList(3);
        service.put(books);

        OperationValueResult<Book> result = service.getByNumberInSequence(1);

        assertTrue(result.success());
        assertNotNull(result.getValue());
        assertEquals(books.get(0), result.getValue());
    }
}