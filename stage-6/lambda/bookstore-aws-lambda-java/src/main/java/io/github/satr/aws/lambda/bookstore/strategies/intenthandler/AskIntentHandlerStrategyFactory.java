package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;

import io.github.satr.aws.lambda.bookstore.entity.formatter.AskMessageFormatter;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;

public class AskIntentHandlerStrategyFactory extends IntentHandlerStrategyFactory {
    public AskIntentHandlerStrategyFactory(BookStorageService bookStorageService, SearchBookResultService searchBookResultService, BasketService basketService) {
        super(bookStorageService, searchBookResultService, basketService, new AskMessageFormatter());
    }

    @Override
    protected IntroductionIntentHandlerStrategy createIntroductionIntentHandlerStrategy() {
        return new AskIntroductionIntentHandlerStrategy();
    }
}
