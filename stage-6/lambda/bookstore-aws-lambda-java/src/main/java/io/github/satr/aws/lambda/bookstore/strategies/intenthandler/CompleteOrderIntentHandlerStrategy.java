package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2022, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.MessageFormatter;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.respond.Response;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import java.util.List;

public class CompleteOrderIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    private final BasketService basketService;

    public CompleteOrderIntentHandlerStrategy(BasketService basketService, MessageFormatter messageFormatter) {
        super(messageFormatter);
        this.basketService = basketService;
    }

    @Override
    public Response handle(Request request, LambdaLogger logger) {
        List<Book> booksInBasket = basketService.getBooks();
        if (booksInBasket.isEmpty())
            return getCloseFulfilledLexRespond(request, "Basket is empty.");

        //TODO - perform order completion

        StringBuilder builder = new StringBuilder(messageFormatter.getBookShortDescriptionListWithPrices(booksInBasket,
                "Thank you for your order. You have bought %s:\n", messageFormatter.amountOfBooks(booksInBasket.size())));
        builder.append(String.format("Total: %.2f", booksInBasket.stream().map(Book::getPrice).reduce(Double::sum).get()));

        basketService.clearBasket();

        return getCloseFulfilledLexRespond(request, builder);
    }
}
