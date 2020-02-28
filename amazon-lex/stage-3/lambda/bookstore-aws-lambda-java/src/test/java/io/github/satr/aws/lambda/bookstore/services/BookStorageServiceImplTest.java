package io.github.satr.aws.lambda.bookstore.services;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class BookStorageServiceImplTest {
    private BookStorageService service;

    @Before
    public void setUp() throws Exception {
        service = new BookStorageServiceImpl();
    }

    @Test
    public void getBookByIsbn() {
        Book bookInStorage = service.getBooksWithTitleStartingWith("monday").get(0);

        Book foundBook = service.getBookByIsbn(bookInStorage.getIsbn());

        assertNotNull(foundBook);
        assertEquals(bookInStorage, foundBook);
    }
}