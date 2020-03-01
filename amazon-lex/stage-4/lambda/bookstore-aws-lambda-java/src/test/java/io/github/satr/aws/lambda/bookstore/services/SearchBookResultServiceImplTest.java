package io.github.satr.aws.lambda.bookstore.services;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.repositories.CustomerBooksRepository;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import io.github.satr.aws.lambda.bookstore.common.OperationValueResult;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotValue;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchBookResultServiceImplTest {
    @Mock
    CustomerBooksRepository customerBooksRepository;
    private SearchBookResultServiceImpl service;

    @Before
    public void setUp() throws Exception {
        service = new SearchBookResultServiceImpl(customerBooksRepository);
    }

    @Test
    public void getByPositionInSequenceFirst() {
        List<Book> books = ObjectMother.getRandomBookList(3);
        when(customerBooksRepository.getBookSearchResult()).thenReturn(books);

        OperationValueResult<Book> result = service.getByPositionInSequence(IntentSlotValue.PositionInSequence.First);

        assertTrue(result.success());
        assertNotNull(result.getValue());
        assertEquals(books.get(0), result.getValue());
    }

    @Test
    public void getByPositionInSequenceLast() {
        List<Book> books = ObjectMother.getRandomBookList(3);
        when(customerBooksRepository.getBookSearchResult()).thenReturn(books);

        OperationValueResult<Book> result = service.getByPositionInSequence(IntentSlotValue.PositionInSequence.Last);

        assertTrue(result.success());
        assertNotNull(result.getValue());
        assertEquals(books.get(2), result.getValue());
    }

    @Test
    public void getByNotExistingPositionInSequenceSeventh() {
        List<Book> books = ObjectMother.getRandomBookList(3);
        when(customerBooksRepository.getBookSearchResult()).thenReturn(books);

        OperationValueResult<Book> result = service.getByPositionInSequence(IntentSlotValue.PositionInSequence.Seventh);

        assertFalse(result.success());
        assertNull(result.getValue());
        assertEquals("Book search result contains only 3 books. Please try again.", result.getErrorsAsString("\n"));
    }

    @Test
    public void getByPositionInSequenceSecond() {
        List<Book> books = ObjectMother.getRandomBookList(3);
        when(customerBooksRepository.getBookSearchResult()).thenReturn(books);

        OperationValueResult<Book> result = service.getByPositionInSequence(IntentSlotValue.PositionInSequence.Second);

        assertTrue(result.success());
        assertNotNull(result.getValue());
        assertEquals(books.get(1), result.getValue());
    }

    @Test
    public void getByNumberInSequenceOne() {
        List<Book> books = ObjectMother.getRandomBookList(3);
        when(customerBooksRepository.getBookSearchResult()).thenReturn(books);

        OperationValueResult<Book> result = service.getByNumberInSequence(1);

        assertTrue(result.success());
        assertNotNull(result.getValue());
        assertEquals(books.get(0), result.getValue());
    }
}