package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotValue;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookListFormatter;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;
import io.github.satr.aws.lambda.bookstore.strategies.booksearch.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchBookByTitleIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    private final BooksWithTitleSearchStrategy booksWithTitleSearchStrategy;
    private final FoundBookListService foundBookListService;
    private Map<String, BookSearchStrategy> bookSearchStrategies = new HashMap<>();

    public SearchBookByTitleIntentHandlerStrategy(BookStorageService bookStorageService, FoundBookListService foundBookListService) {
        this.foundBookListService = foundBookListService;
        bookSearchStrategies.put(IntentSlotValue.WordsPosition.Starts, new BooksWithTitleStartingWithTextSearchStrategy(bookStorageService));
        bookSearchStrategies.put(IntentSlotValue.WordsPosition.Ends, new BooksWithTitleEndingWithTextSearchStrategy(bookStorageService));
        bookSearchStrategies.put(IntentSlotValue.WordsPosition.Contains, new BooksWithTitleContainingTextSearchStrategy(bookStorageService));
        booksWithTitleSearchStrategy = new BooksWithTitleSearchStrategy(bookStorageService);
    }

    @Override
    public LexRespond handle(LexRequest request, LambdaLogger logger) {
        StringBuilder responseMessageBuilder = new StringBuilder();

        String wordsPosition = request.getSlot(IntentSlotName.WordsPosition);
        String titleQuery = request.getSlot(IntentSlotName.BookTitle);
        responseMessageBuilder.append(getQueryDescription(wordsPosition, titleQuery) + "\n");

        List<Book> foundBookList = getBookSearchStrategyBy(wordsPosition).queryBy(titleQuery);
        foundBookListService.put(foundBookList);

        if (foundBookList.isEmpty()) {
            responseMessageBuilder.append("No books found. Please try another criteria.");
            return getCloseFulfilledLexRespond(request, responseMessageBuilder);
        }

        String resultMessage = BookListFormatter.getShortDescriptionList(foundBookList, "Found %d books:\n", foundBookList.size());
        responseMessageBuilder.append(resultMessage);

        return getCloseFulfilledLexRespond(request, responseMessageBuilder);
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
