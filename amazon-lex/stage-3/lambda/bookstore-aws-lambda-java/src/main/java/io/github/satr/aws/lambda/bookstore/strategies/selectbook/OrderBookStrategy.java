package io.github.satr.aws.lambda.bookstore.strategies.selectbook;

import io.github.satr.aws.lambda.bookstore.entity.Basket;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookFormatter;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.respond.Message;
import io.github.satr.aws.lambda.bookstore.services.BookOrderService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;

public class OrderBookStrategy extends AbstractSelectBookStrategy {
    private final BookOrderService bookOrderService;

    public OrderBookStrategy(BookStorageService bookStorageService, FoundBookListService foundBookListService, BookOrderService bookOrderService) {
        super(bookStorageService, foundBookListService);
        this.bookOrderService = bookOrderService;
    }

    @Override
    protected void processCustom(LexRespond respond, Book selectedBook) {
        Basket basket = bookOrderService.getBasket();
        Message message = respond.getDialogAction().getMessage();
        if(basket.containsIsbn(selectedBook.getIsbn())) {
            message.setContent("This book is already ordered.");
            return;
        }
        basket.add(selectedBook);
        message.setContentFormatted("Book added to basket: %s", BookFormatter.getShortDescription(selectedBook));
    }
}
