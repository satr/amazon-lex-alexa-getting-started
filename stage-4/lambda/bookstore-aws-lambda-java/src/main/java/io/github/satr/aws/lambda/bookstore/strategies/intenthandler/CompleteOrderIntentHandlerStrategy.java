package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookListFormatter;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.response.Response;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import java.util.List;

public class CompleteOrderIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    private final BasketService basketService;

    public CompleteOrderIntentHandlerStrategy(BasketService basketService) {
        this.basketService = basketService;
    }

    @Override
    public Response handle(Request request, LambdaLogger logger) {
        List<Book> booksInBasket = basketService.getBooks();
        if (booksInBasket.isEmpty())
            return getCloseFulfilledLexRespond(request, "Basket is empty.");

        //TODO - perform order completion

        StringBuilder builder = new StringBuilder(BookListFormatter.getShortDescriptionListWithPrices(booksInBasket,
                "Thank you for your order. You have bought %s:\n", BookListFormatter.amountOfBooks(booksInBasket.size())));
        builder.append(String.format("Total: %.2f", booksInBasket.stream().map(Book::getPrice).reduce(Double::sum).get()));

        basketService.clearBasket();

        return getCloseFulfilledLexRespond(request, builder);
    }
}
