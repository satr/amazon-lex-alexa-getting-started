package io.github.satr.aws.lambda.bookstore.strategies.selectbook;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookFormatter;
import io.github.satr.aws.lambda.bookstore.response.Response;
import io.github.satr.aws.lambda.bookstore.response.Message;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;

public class AddBookToBasketStrategy extends AbstractSelectBookStrategy {
    private final BasketService basketService;

    public AddBookToBasketStrategy(SearchBookResultService searchBookResultService, BasketService basketService) {
        super(searchBookResultService);
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
        message.setContentFormatted("Book added to basket: %s", BookFormatter.getShortDescription(selectedBook));
    }
}
