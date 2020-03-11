package io.github.satr.aws.lambda.bookstore.repositories;

import io.github.satr.aws.lambda.bookstore.common.TestHelper;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.repositories.database.TableHelper;
import io.github.satr.aws.lambda.bookstore.repositories.database.tableentity.BasketItem;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CustomerBooksRepositoryImplBasketTest extends AbstractCustomerBooksRepositoryImplTest {

    public void customSetUp() {
        TableHelper.createTableBasket(dynamoDbClient);
    }

    @After
    public void tearDown() throws Exception {
        TableHelper.deleteTableBasket(dynamoDbClient);
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
        assertEquals(repBooks.get(0).getIsbn(), basketBooks.get(0).getIsbn());
        assertEquals(repBooks.get(0).getAuthor(), basketBooks.get(0).getAuthor());
        assertEquals(repBooks.get(0).getTitle(), basketBooks.get(0).getTitle());
        assertEquals(repBooks.get(0).getIssueYear(), basketBooks.get(0).getIssueYear());
        assertEquals(repBooks.get(0).getPrice(), basketBooks.get(0).getPrice(), 0.0001f);
        assertTrue(basketBooks.stream().allMatch(resBook -> repBooks.stream().anyMatch(repBook -> TestHelper.isEqual(resBook, repBook))));
    }
}