package io.github.satr.aws.lambda.bookstore.services;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasketServiceImplTest {

    private BasketServiceImpl service;

    @Before
    public void setUp() throws Exception {
        service = new BasketServiceImpl();
    }

    @Test
    public void getExistingBookByIsbn() {
        Book bookInBasket = ObjectMother.getRandomBook();
        service.add(bookInBasket);

        Book book = service.getBookByIsbn(bookInBasket.getIsbn());

        assertNotNull(book);
        assertEquals(bookInBasket, book);
    }

    @Test
    public void getNotExistingBookByIsbn() {
        service.add(ObjectMother.getRandomBook());

        Book book = service.getBookByIsbn(ObjectMother.getRandomString());

        assertNull(book);
    }
}