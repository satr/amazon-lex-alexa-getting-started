package io.github.satr.aws.lambda.bookstore.repositories.localdatabase;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.common.TestHelper;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.repositories.database.tableentity.BookSearchResultItem;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/*
* For each running test - the table "BookSearchResult" is created in the local DynamoDb instance and deleted afterwards
* */
public class CustomerBooksRepositoryImplBookSearchResultForLocalDatabaseTest extends AbstractCustomerBooksRepositoryImplForLocalDatabaseTest {

    public void customSetUp() {
        LocalDatabaseTableHelper.createTableBookSearchResult(dynamoDbClient);
    }

    @After
    public void tearDown() throws Exception {
        LocalDatabaseTableHelper.deleteTableBookSearchResult(dynamoDbClient);
    }

    @Test
    public void putToBookSearchResult() {
        List<BookSearchResultItem> books = ObjectMother.getRandomBookSearchResultItemList(3);

        repository.putToBookSearchResult(books);
    }

    @Test
    public void getBookSearchResult() {
        List<BookSearchResultItem> repBooks = ObjectMother.getRandomBookSearchResultItemList(3);
        repository.putToBookSearchResult(repBooks);

        List<Book> searchResult = repository.getBookSearchResult();

        assertNotNull(searchResult);
        assertEquals(repBooks.size(), searchResult.size());
        BookSearchResultItem oneItem = repBooks.stream().filter(b -> b.getIsbn().equals(searchResult.get(0).getIsbn())).findFirst().orElse(null);
        assertNotNull(oneItem);
        assertEquals(oneItem.getAuthor(), searchResult.get(0).getAuthor());
        assertEquals(oneItem.getTitle(), searchResult.get(0).getTitle());
        assertEquals(oneItem.getIssueYear(), searchResult.get(0).getIssueYear());
        assertEquals(oneItem.getPrice(), searchResult.get(0).getPrice(), 0.0001f);
        assertTrue(searchResult.stream().allMatch(resBook -> repBooks.stream().anyMatch(repBook -> TestHelper.isEqual(resBook, repBook))));
    }

}