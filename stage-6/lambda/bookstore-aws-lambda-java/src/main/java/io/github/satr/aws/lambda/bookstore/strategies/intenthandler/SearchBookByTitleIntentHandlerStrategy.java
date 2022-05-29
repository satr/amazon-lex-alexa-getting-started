package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2022, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotValue;
import io.github.satr.aws.lambda.bookstore.constants.SessionAttributeKey;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.MessageFormatter;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.respond.Response;
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

    public SearchBookByTitleIntentHandlerStrategy(BookStorageService bookStorageService, SearchBookResultService searchBookResultService, MessageFormatter messageFormatter) {
        super(messageFormatter);
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
        if(titleQuery == null || titleQuery.length() < 2) {
            logger.log(String.format("SearchBookByTitle: WordsPosition=\"%s\", BookTitle=\"%s\"", wordsPosition, titleQuery));
            return getCloseFulfilledLexRespond(request, getInvalidTitleMessage(wordsPosition));
        }

        responseMessageBuilder.append(getQueryDescription(wordsPosition, titleQuery) + "\n");

        List<Book> bookSearchResult = getBookSearchStrategyBy(wordsPosition).queryBy(titleQuery);
        searchBookResultService.put(bookSearchResult);

        if (bookSearchResult.isEmpty()) {
            responseMessageBuilder.append("No books found. Please try another criteria.");
            return getCloseFulfilledLexRespond(request, responseMessageBuilder);
        }

        responseMessageBuilder.append(messageFormatter.getBookShortDescriptionList(bookSearchResult,
                        "Found %s:\n", messageFormatter.amountOfBooks(bookSearchResult.size())));

        Response respond = getCloseFulfilledLexRespond(request, responseMessageBuilder);

        if(bookSearchResult.size() == 1)
            respond.setSessionAttribute(SessionAttributeKey.SelectedBookIsbn, bookSearchResult.get(0).getIsbn());//auto-select the only found book
        else
            respond.removeSessionAttribute(SessionAttributeKey.SelectedBookIsbn);

        return respond;
    }

    private StringBuilder getInvalidTitleMessage(String wordsPosition) {
        StringBuilder builder = new StringBuilder("Sorry, could you repeat what a title " + wordsPosition);
        if(!wordsPosition.equals(IntentSlotValue.WordsPosition.Contains))
            builder.append(" with");
        builder.append("?");
        return builder;
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
