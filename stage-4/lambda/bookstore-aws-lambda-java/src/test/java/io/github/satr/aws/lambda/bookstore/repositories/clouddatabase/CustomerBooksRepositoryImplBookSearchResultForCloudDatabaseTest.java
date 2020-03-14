package io.github.satr.aws.lambda.bookstore.repositories.clouddatabase;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.common.TestHelper;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.repositories.database.tableentity.BookSearchResultItem;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/*
 *  AWS DynamoDb should contain a table:
 *      Table name: BookSearchResult
 *      Primary partition key: isbn (String), case-sensitive
 *  In the region "us-east-1". Otherwise - specify the region in the super-class AbstractCustomerBooksRepositoryImplForCloudDatabaseTest
 */

//Comment following "@org.junit.Ignore" attribute to run these tests with real AWS DynamoDb table
@org.junit.Ignore
public class CustomerBooksRepositoryImplBookSearchResultForCloudDatabaseTest extends AbstractCustomerBooksRepositoryImplForCloudDatabaseTest {
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        repository.clearBookSearchResult();
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