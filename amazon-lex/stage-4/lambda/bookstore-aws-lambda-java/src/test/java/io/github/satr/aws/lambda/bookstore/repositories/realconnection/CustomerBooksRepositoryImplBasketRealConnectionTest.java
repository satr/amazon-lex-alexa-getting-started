package io.github.satr.aws.lambda.bookstore.repositories.realconnection;

import io.github.satr.aws.lambda.bookstore.common.TestHelper;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.repositories.database.tableentity.BasketItem;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CustomerBooksRepositoryImplBasketRealConnectionTest extends AbstractCustomerBooksRepositoryImplRealConnectionTest {
    @Test
    public void clearBasket() {
        List<BasketItem> repBooks = ObjectMother.getRandomBasketItemList(3);
        for (BasketItem book: repBooks)
            repository.addToBasket(book);

        repository.clearBasket();

        assertEquals(0, repository.getBasketSize());
    }

    @Test
    public void removeFromBasket() {
        List<BasketItem> repBooks = ObjectMother.getRandomBasketItemList(3);
        for (BasketItem book: repBooks)
            repository.addToBasket(book);

        repository.removeFromBasket(repBooks.get(1));

        List<Book> basketBooks = repository.getBasketBooks();
        assertEquals(repBooks.size() - 1, basketBooks.size());
        assertNotNull(basketBooks.stream().anyMatch(b -> b.getIsbn().equals(repBooks.get(0).getIsbn())));
        assertNotNull(basketBooks.stream().anyMatch(b -> b.getIsbn().equals(repBooks.get(2).getIsbn())));
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
        for (Book book: repBooks)
            repository.addToBasket(book);

        List<Book> basketBooks = repository.getBasketBooks();

        assertNotNull(basketBooks);
        assertEquals(repBooks.size(), basketBooks.size());
        BasketItem basketItem = repBooks.stream().filter(b -> b.getIsbn().equals(basketBooks.get(0).getIsbn())).findFirst().orElse(null);
        assertNotNull(basketItem);
        assertEquals(basketItem.getAuthor(), basketBooks.get(0).getAuthor());
        assertEquals(basketItem.getTitle(), basketBooks.get(0).getTitle());
        assertEquals(basketItem.getIssueYear(), basketBooks.get(0).getIssueYear());
        assertEquals(basketItem.getPrice(), basketBooks.get(0).getPrice(), 0.0001f);
        assertTrue(basketBooks.stream().allMatch(resBook -> repBooks.stream().anyMatch(repBook -> TestHelper.isEqual(resBook, repBook))));
    }
}