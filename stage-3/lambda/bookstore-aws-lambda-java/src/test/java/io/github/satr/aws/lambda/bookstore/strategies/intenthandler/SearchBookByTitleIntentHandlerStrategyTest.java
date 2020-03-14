package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchBookByTitleIntentHandlerStrategyTest {
    @Mock
    LambdaLogger lambdaLogger;
    @Mock
    BookStorageService bookStorageService;
    @Mock
    SearchBookResultService searchBookResultService;
    private LexRequest request;
    private SearchBookByTitleIntentHandlerStrategy strategy;

    @Before
    public void setUp() throws Exception {
        request = ObjectMother.createLexRequestFromJson("simple-search-book-by-title-intent-request.json");
        strategy = new SearchBookByTitleIntentHandlerStrategy(bookStorageService, searchBookResultService);
    }

    @Test
    public void handleRequestReturnsNotEmptyRespond() {
        LexRespond respond = strategy.handle(request, lambdaLogger);

        assertNotNull(respond);
        assertNotNull(respond.getDialogAction());
        assertNotNull(respond.getDialogAction().getMessage());
    }

    @Test
    public void handleRequestWithSessionReturnsRespondWithSession() {
        String attrKey1 = ObjectMother.getRandomString();
        String attrValue1 = ObjectMother.getRandomString();
        String attrKey2 = ObjectMother.getRandomString();
        String attrValue2 = ObjectMother.getRandomString();
        request.getSessionAttributes().put(attrKey1, attrValue1);
        request.getSessionAttributes().put(attrKey2, attrValue2);
        request.setSessionId(null);

        LexRespond respond = strategy.handle(request, lambdaLogger);

        Map<String, Object> sessionAttributes = respond.getSessionAttributes();
        assertNotNull(sessionAttributes);
        assertEquals(attrValue1, sessionAttributes.get(attrKey1));
        assertEquals(attrValue2, sessionAttributes.get(attrKey2));
//        assertNotNull(respond.getSessionId());
//        assertTrue(respond.getSessionId().length() > 0);
    }

    @Test
    public void searchBookTitleWithoutWordsPositionWhenNoSuchBooksReturnsNoBooks() {
        String titleQuery = ObjectMother.getRandomString();
        setupSearchForBook(titleQuery, null, 0);

        LexRespond respond = strategy.handle(request, lambdaLogger);

        String respondMessage = respond.getDialogAction().getMessage().getContent();
        assertTrue(respondMessage.contains(String.format("Searched books by title: \"%s\"", titleQuery)));
        assertTrue(respondMessage.contains("No books found. Please try another criteria."));
    }

    @Test
    public void searchBookTitleWithWordsPositionWhenNoSuchBooksReturnsNoBooks() {
        String titleQuery = ObjectMother.getRandomString();
        String wordsPosition = "starts";
        setupSearchForBook(titleQuery, wordsPosition, 0);

        LexRespond respond = strategy.handle(request, lambdaLogger);

        String respondMessage = respond.getDialogAction().getMessage().getContent();
        assertTrue(respondMessage.contains(String.format("Searched books \"%s\" in title \"%s\"", wordsPosition, titleQuery)));
        assertTrue(respondMessage.contains("No books found. Please try another criteria."));
    }

    @Test
    public void searchBookWhenBooksFoundReturnsBooksList() {
        String titleQuery = ObjectMother.getRandomString();
        List<Book> bookSearchResult = setupSearchForBook(titleQuery, null, 3);

        LexRespond respond = strategy.handle(request, lambdaLogger);

        String respondMessage = respond.getDialogAction().getMessage().getContent();
        String[] respondMessageLines = respondMessage.split("\n");
        assertEquals(String.format("Searched books by title: \"%s\"", titleQuery), respondMessageLines[0]);
        assertEquals("Found 3 books:", respondMessageLines[1]);
        assertEquals(getBookSearchResultItemDescription(bookSearchResult, 0), respondMessageLines[2]);
        assertEquals(getBookSearchResultItemDescription(bookSearchResult, 1), respondMessageLines[3]);
        assertEquals(getBookSearchResultItemDescription(bookSearchResult, 2), respondMessageLines[4]);
    }

    private String getBookSearchResultItemDescription(List<Book> books, int itemIndex) {
        return String.format("%d. \"%s\" by %s", itemIndex + 1, books.get(itemIndex).getTitle(), books.get(itemIndex).getAuthor());
    }

    private List<Book> setupSearchForBook(String titleQuery, Object wordsPosition, int bookSearchResultCount) {
        request.getSlots().put(IntentSlotName.BookTitle, titleQuery);
        request.getSlots().put(IntentSlotName.WordsPosition, wordsPosition);
        List<Book> bookList = ObjectMother.getRandomBookList(bookSearchResultCount);
        when(bookStorageService.getBooksWithTitle(titleQuery)).thenReturn(bookList);
        when(bookStorageService.getBooksWithTitleStartingWith(titleQuery)).thenReturn(bookList);
        when(bookStorageService.getBooksWithTitleEndingWith(titleQuery)).thenReturn(bookList);
        when(bookStorageService.getBooksWithTitleContaining(titleQuery)).thenReturn(bookList);
        return bookList;
    }
}