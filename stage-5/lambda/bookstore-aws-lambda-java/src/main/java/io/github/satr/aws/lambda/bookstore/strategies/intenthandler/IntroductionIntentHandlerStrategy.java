package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.respond.Response;

public class IntroductionIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    @Override
    public Response handle(Request request, LambdaLogger logger) {
        String message = "Hi! This is a book store bot to assist in ordering books.\n" +
                "Tell, for example:\n" +
                "Do you have a book which title contains Friday\n" +
                "Do you have a book which title starts with \"Monday\"\n" +
                "Show the second book\n" +
                "Order the book number 3\n" +
                "Show book search result\n" +
                "Add this book to basket\n" +
                "Remove first book from basket\n" +
                "Delete from basket book number 3\n" +
                "Show the basket\n" +
                "Complete the order";
        return getCloseFulfilledLexRespond(request, message);
    }
}
