package io.github.satr.aws.lambda.bookstore.strategies.selectbook;

import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookFormatter;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.respond.Message;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;

public class AddBookToBasketStrategy extends AbstractSelectBookStrategy {
    private final BasketService basketService;

    public AddBookToBasketStrategy(BookStorageService bookStorageService, FoundBookListService foundBookListService, BasketService basketService) {
        super(bookStorageService, foundBookListService);
        this.basketService = basketService;
    }

    @Override
    protected void processCustom(LexRespond respond, Book selectedBook) {
        Message message = respond.getDialogAction().getMessage();
        if(basketService.getBookByIsbn(selectedBook.getIsbn()) != null) {
            message.setContent("This book is already in basket.");
            return;
        }
        basketService.add(selectedBook);
        message.setContentFormatted("Book added to basket: %s", BookFormatter.getShortDescription(selectedBook));
    }
}
