package io.github.satr.aws.lambda.bookstore.strategies.selectbook;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.MessageFormatter;
import io.github.satr.aws.lambda.bookstore.respond.Response;
import io.github.satr.aws.lambda.bookstore.respond.Message;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;

public class AddBookToBasketStrategy extends AbstractSelectBookStrategy {
    private final BasketService basketService;

    public AddBookToBasketStrategy(SearchBookResultService searchBookResultService, BasketService basketService, MessageFormatter messageFormatter) {
        super(searchBookResultService, messageFormatter);
        this.basketService = basketService;
    }

    @Override
    protected void processCustom(Response respond, Book selectedBook) {
        Message message = respond.getDialogAction().getMessage();
        if(basketService.getBookByIsbn(selectedBook.getIsbn()) != null) {
            message.setContent("This book is already in basket.");
            return;
        }
        basketService.add(selectedBook);
        message.setContentFormatted("Book added to basket: %s", messageFormatter.getBookShortDescription(selectedBook));
    }
}
