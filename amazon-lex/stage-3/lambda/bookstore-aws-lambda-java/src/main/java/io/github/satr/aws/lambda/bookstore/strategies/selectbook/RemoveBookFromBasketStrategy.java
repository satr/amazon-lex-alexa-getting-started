package io.github.satr.aws.lambda.bookstore.strategies.selectbook;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookFormatter;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.respond.Message;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;

public class RemoveBookFromBasketStrategy extends AbstractSelectBookStrategy {
    private final BasketService basketService;

    public RemoveBookFromBasketStrategy(FoundBookListService foundBookListService, BasketService basketService) {
        super(foundBookListService);
        this.basketService = basketService;
    }

    @Override
    protected void processCustom(LexRespond respond, Book selectedBook) {
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
        builder.append(String.format("Book removed from the basket: %s", BookFormatter.getShortDescription(selectedBook)));
        if(basketService.getBookCount() == 0)
            builder.append("\nBasket is empty.");
        message.setContent(builder);
    }
}
