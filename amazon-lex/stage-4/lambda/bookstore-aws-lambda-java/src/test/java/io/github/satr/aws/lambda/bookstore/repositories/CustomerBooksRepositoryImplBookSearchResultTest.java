package io.github.satr.aws.lambda.bookstore.repositories;

import io.github.satr.aws.lambda.bookstore.common.TestHelper;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.repositories.database.TableHelper;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CustomerBooksRepositoryImplBookSearchResultTest extends AbstractCustomerBooksRepositoryImplTest {

    public void customSetUp() {
        TableHelper.createTableBookSearchResult(dynamoDbClient);
    }

    @After
    public void tearDown() throws Exception {
        TableHelper.deleteTableBookSearchResult(dynamoDbClient);
    }

    @Test
    public void putToBookSearchResult() {
        List<Book> books = ObjectMother.getRandomBookList(3);

        repository.putToBookSearchResult(books);
    }

    @Test
    public void getBookSearchResult() {
        List<Book> repBooks = ObjectMother.getRandomBookList(3);
        repository.putToBookSearchResult(repBooks);

        List<Book> searchResult = repository.getBookSearchResult();

        assertNotNull(searchResult);
        assertEquals(repBooks.size(), searchResult.size());
        assertEquals(repBooks.get(0).getIsbn(), searchResult.get(0).getIsbn());
        assertEquals(repBooks.get(0).getAuthor(), searchResult.get(0).getAuthor());
        assertEquals(repBooks.get(0).getTitle(), searchResult.get(0).getTitle());
        assertEquals(repBooks.get(0).getIssueYear(), searchResult.get(0).getIssueYear());
        assertEquals(repBooks.get(0).getPrice(), searchResult.get(0).getPrice(), 0.0001f);
        assertTrue(searchResult.stream().allMatch(resBook -> repBooks.stream().anyMatch(repBook -> TestHelper.isEqual(resBook, repBook))));
    }

}