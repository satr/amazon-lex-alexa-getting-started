package io.github.satr.aws.lambda.bookstore.services;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.repositories.CustomerBooksRepository;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasketServiceImplTest {
    @Mock
    CustomerBooksRepository customerBooksRepository;
    private BasketServiceImpl service;

    @Before
    public void setUp() throws Exception {
        service = new BasketServiceImpl(customerBooksRepository);
    }

    @Test
    public void getExistingBookByIsbn() {
        Book bookInBasket = ObjectMother.getRandomBook();
        when(customerBooksRepository.getBasketBooks()).thenReturn(Arrays.asList(bookInBasket));

        Book book = service.getBookByIsbn(bookInBasket.getIsbn());

        assertNotNull(book);
        assertEquals(bookInBasket, book);
    }

    @Test
    public void getNotExistingBookByIsbn() {
        when(customerBooksRepository.getBasketBooks()).thenReturn(ObjectMother.getRandomBookList(3));

        Book book = service.getBookByIsbn(ObjectMother.getRandomString());

        assertNull(book);
    }
}