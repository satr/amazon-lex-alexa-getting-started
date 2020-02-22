package io.github.satr.aws.lambda.bookstore.strategies;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;

public class SearchBookByTitleIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    @Override
    public LexRespond handle(LexRequest request, LambdaLogger logger) {
        String wordsPosition = (String) request.getSlots().get(IntentSlot.WordsPosition);
        String bookTitle = (String) request.getSlots().get(IntentSlot.BookTitle);
        return wordsPosition == null
                ? getCloseFulfilledLexRespond("Searched a book by title: \"%s\"", bookTitle)
                : getCloseFulfilledLexRespond("Searched a book \"%s\" in title \"%s\"",
                                                             wordsPosition, bookTitle);
    }
}
