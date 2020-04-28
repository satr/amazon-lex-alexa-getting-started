package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.respond.Response;

public abstract class IntroductionIntentHandlerStrategy extends AbstractIntentHandlerStrategy {

    public IntroductionIntentHandlerStrategy() {
        super(null);
    }

    @Override
    public abstract Response handle(Request request, LambdaLogger logger) ;
}
