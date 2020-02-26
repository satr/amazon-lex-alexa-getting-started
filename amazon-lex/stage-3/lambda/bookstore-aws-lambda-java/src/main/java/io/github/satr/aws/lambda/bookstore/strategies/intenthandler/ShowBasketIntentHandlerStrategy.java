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
        return booksInBasket.isEmpty()
                ? getCloseFulfilledLexRespond(request, "Basket is empty.")
                : getCloseFulfilledLexRespond(request, BookListFormatter.shortDescriptionList(booksInBasket,
                                   "Basket contains %d books:\n", booksInBasket.size()));
    }
}
