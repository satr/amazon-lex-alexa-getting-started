package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.respond.Response;

public class AskIntroductionIntentHandlerStrategy extends IntroductionIntentHandlerStrategy {
    @Override
    public Response handle(Request request, LambdaLogger logger) {
        String message = "Hi! This is a book store bot to assist in ordering books. What can I do for you?";
        return getCloseFulfilledLexRespond(request, message);
    }
}
