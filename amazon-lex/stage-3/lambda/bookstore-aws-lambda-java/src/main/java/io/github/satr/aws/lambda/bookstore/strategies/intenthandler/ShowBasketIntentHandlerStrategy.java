package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookListFormatter;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.services.BasketService;

import java.util.List;

public class ShowBasketIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    private final BasketService basketService;

    public ShowBasketIntentHandlerStrategy(BasketService basketService) {
        this.basketService = basketService;
    }

    @Override
    public LexRespond handle(LexRequest request, LambdaLogger logger) {
        List<Book> booksInBasket = basketService.getBooks();
        if (booksInBasket.isEmpty())
            return getCloseFulfilledLexRespond(request, "Basket is empty.");
        StringBuilder builder = new StringBuilder(BookListFormatter.getShortDescriptionListWithPrices(booksInBasket,
                "Basket contains %d books:\n", booksInBasket.size()));
        builder.append(String.format("Total: %.2f", booksInBasket.stream().map(Book::getPrice).reduce(Double::sum).get()));
        return getCloseFulfilledLexRespond(request, builder);
    }
}
