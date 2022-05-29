package io.github.satr.aws.lambda.bookstore.repositories.localdatabase;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.common.TestHelper;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.repositories.database.tableentity.BasketItem;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/*
 * For each running test - the table "Basket" is created in the local DynamoDb instance and deleted afterwards
 * */
public class CustomerBooksRepositoryImplBasketForLocalDatabaseTest extends AbstractCustomerBooksRepositoryImplForLocalDatabaseTest {

    public void customSetUp() {
        LocalDatabaseTableHelper.createTableBasket(dynamoDbClient);
    }

    @After
    public void tearDown() throws Exception {
        LocalDatabaseTableHelper.deleteTableBasket(dynamoDbClient);
    }

    @Test
    public void clearBasket() {
    }

    @Test
    public void removeFromBasket() {

    }

    @Test
    public void getBasketSize() {
        List<BasketItem> repBooks = ObjectMother.getRandomBasketItemList(3);
        for (BasketItem book: repBooks)
            repository.addToBasket(book);

        int basketSize = repository.getBasketSize();

        assertEquals(repBooks.size(), basketSize);
    }

    @Test
    public void getBasketBooks() {
        List<BasketItem> repBooks = ObjectMother.getRandomBasketItemList(3);
        for (BasketItem book: repBooks)
            repository.addToBasket(book);

        List<Book> basketBooks = repository.getBasketBooks();

        assertNotNull(basketBooks);
        assertEquals(repBooks.size(), basketBooks.size());
        BasketItem oneItem = repBooks.stream().filter(b -> b.getIsbn().equals(basketBooks.get(0).getIsbn())).findFirst().orElse(null);
        assertNotNull(oneItem);
        assertEquals(oneItem.getAuthor(), basketBooks.get(0).getAuthor());
        assertEquals(oneItem.getTitle(), basketBooks.get(0).getTitle());
        assertEquals(oneItem.getIssueYear(), basketBooks.get(0).getIssueYear());
        assertEquals(oneItem.getPrice(), basketBooks.get(0).getPrice(), 0.0001f);
        assertTrue(basketBooks.stream().allMatch(resBook -> repBooks.stream().anyMatch(repBook -> TestHelper.isEqual(resBook, repBook))));
    }
}