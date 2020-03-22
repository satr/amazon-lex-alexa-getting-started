package io.github.satr.aws.lambda.bookstore.strategies.selectbook;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookFormatter;
import io.github.satr.aws.lambda.bookstore.response.Response;
import io.github.satr.aws.lambda.bookstore.response.Message;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;

public class RemoveBookFromBasketStrategy extends AbstractSelectBookStrategy {
    private final BasketService basketService;

    public RemoveBookFromBasketStrategy(SearchBookResultService searchBookResultService, BasketService basketService) {
        super(searchBookResultService);
        this.basketService = basketService;
    }

    @Override
    protected void processCustom(Response response, Book selectedBook) {
        Message message = response.getDialogAction().getMessage();
        if(basketService.getBookCount() == 0) {
            message.setContent("Basket is empty.");
            return;
        }
        if(basketService.getBookByIsbn(selectedBook.getIsbn()) == null) {
            message.setContentFormatted("This book is not in the basket. %s", selectedBook.getIsbn());
            return;
        }
        basketService.remove(selectedBook);
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Book removed from the basket: %s", BookFormatter.getShortDescription(selectedBook)));
        if(basketService.getBookCount() == 0)
            builder.append("\nBasket is empty.");
        message.setContent(builder);
    }
}
