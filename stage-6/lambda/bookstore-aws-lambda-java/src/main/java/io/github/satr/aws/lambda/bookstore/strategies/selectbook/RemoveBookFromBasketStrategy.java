package io.github.satr.aws.lambda.bookstore.strategies.selectbook;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.MessageFormatter;
import io.github.satr.aws.lambda.bookstore.respond.Response;
import io.github.satr.aws.lambda.bookstore.respond.Message;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;

public class RemoveBookFromBasketStrategy extends AbstractSelectBookStrategy {
    private final BasketService basketService;

    public RemoveBookFromBasketStrategy(SearchBookResultService searchBookResultService, BasketService basketService, MessageFormatter messageFormatter) {
        super(searchBookResultService, messageFormatter);
        this.basketService = basketService;
    }

    @Override
    protected void processCustom(Response respond, Book selectedBook) {
        Message message = respond.getDialogAction().getMessage();
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
        builder.append(String.format("Book removed from the basket: %s", messageFormatter.getBookShortDescription(selectedBook)));
        if(basketService.getBookCount() == 0)
            builder.append("\nBasket is empty.");
        message.setContent(builder);
    }
}
