package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotValue;
import io.github.satr.aws.lambda.bookstore.constants.SessionAttributeKey;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookListFormatter;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.response.Response;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;
import io.github.satr.aws.lambda.bookstore.strategies.booksearch.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchBookByTitleIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    private final BooksWithTitleSearchStrategy booksWithTitleSearchStrategy;
    private final SearchBookResultService searchBookResultService;
    private Map<String, BookSearchStrategy> bookSearchStrategies = new HashMap<>();

    public SearchBookByTitleIntentHandlerStrategy(BookStorageService bookStorageService, SearchBookResultService searchBookResultService) {
        this.searchBookResultService = searchBookResultService;
        bookSearchStrategies.put(IntentSlotValue.WordsPosition.Starts, new BooksWithTitleStartingWithTextSearchStrategy(bookStorageService));
        bookSearchStrategies.put(IntentSlotValue.WordsPosition.Ends, new BooksWithTitleEndingWithTextSearchStrategy(bookStorageService));
        bookSearchStrategies.put(IntentSlotValue.WordsPosition.Contains, new BooksWithTitleContainingTextSearchStrategy(bookStorageService));
        booksWithTitleSearchStrategy = new BooksWithTitleSearchStrategy(bookStorageService);
    }

    @Override
    public Response handle(Request request, LambdaLogger logger) {
        StringBuilder responseMessageBuilder = new StringBuilder();

        String wordsPosition = request.getSlot(IntentSlotName.WordsPosition);
        String titleQuery = request.getSlot(IntentSlotName.BookTitle);
        responseMessageBuilder.append(getQueryDescription(wordsPosition, titleQuery) + "\n");

        List<Book> bookSearchResult = getBookSearchStrategyBy(wordsPosition).queryBy(titleQuery);
        searchBookResultService.put(bookSearchResult);

        if (bookSearchResult.isEmpty()) {
            responseMessageBuilder.append("No books found. Please try another criteria.");
            return getCloseFulfilledLexRespond(request, responseMessageBuilder);
        }

        responseMessageBuilder.append(BookListFormatter.getShortDescriptionList(bookSearchResult,
                        "Found %s:\n", BookListFormatter.amountOfBooks(bookSearchResult.size())));

        Response respond = getCloseFulfilledLexRespond(request, responseMessageBuilder);

        if(bookSearchResult.size() == 1)
            respond.setSessionAttribute(SessionAttributeKey.SelectedBookIsbn, bookSearchResult.get(0).getIsbn());//auto-select the only found book

        return respond;
    }

    private BookSearchStrategy getBookSearchStrategyBy(String wordsPosition) {
        return bookSearchStrategies.getOrDefault(wordsPosition, booksWithTitleSearchStrategy);
    }

    private String getQueryDescription(String wordsPosition, String titleQuery) {
        return wordsPosition == null
               ? String.format("Searched books by title: \"%s\"", titleQuery)
               : String.format("Searched books \"%s\" in title \"%s\"", wordsPosition, titleQuery);
    }
}
