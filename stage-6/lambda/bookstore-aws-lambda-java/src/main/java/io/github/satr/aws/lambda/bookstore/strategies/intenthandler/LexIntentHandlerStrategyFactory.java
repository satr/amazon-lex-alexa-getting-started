package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;

import io.github.satr.aws.lambda.bookstore.entity.formatter.LexMessageFormatter;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;

public class LexIntentHandlerStrategyFactory extends IntentHandlerStrategyFactory {
    public LexIntentHandlerStrategyFactory(BookStorageService bookStorageService, SearchBookResultService searchBookResultService, BasketService basketService) {
        super(bookStorageService, searchBookResultService, basketService, new LexMessageFormatter());
    }

    @Override
    protected IntroductionIntentHandlerStrategy createIntroductionIntentHandlerStrategy() {
        return new LexIntroductionIntentHandlerStrategy();
    }
}
