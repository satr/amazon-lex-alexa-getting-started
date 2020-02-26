package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;

public class IntroductionIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    @Override
    public LexRespond handle(LexRequest request, LambdaLogger logger) {
        String message = "Hi! This is a book store bot to assist in ordering books.\n" +
                "Tell, for example:\n" +
                "'Do you have a book which title starts with \"Monday\"'\n" +
                "'Show the second book'\n" +
                "'I will order book number 3'\n" +
                "'Show the found book list'\n" +
                "'Show the last search result'\n'" +
                "'Add this book to basket'\n" +
                "'Show the basket...'";
        return getCloseFulfilledLexRespond(request, message);
    }
}
