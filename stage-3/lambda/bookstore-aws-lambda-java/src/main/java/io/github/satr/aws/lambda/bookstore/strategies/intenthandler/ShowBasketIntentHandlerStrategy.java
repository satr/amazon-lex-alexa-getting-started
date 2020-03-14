package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.BookListFormatter;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.respond.responsecard.ResponseCard;
import io.github.satr.aws.lambda.bookstore.services.BasketService;

import java.util.List;

// Copyright © 2020, github.com/satr, MIT License

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
                "Basket contains %s:\n", BookListFormatter.amountOfBooks(booksInBasket.size())));
        builder.append(String.format("Total: %.2f", booksInBasket.stream().map(Book::getPrice).reduce(Double::sum).get()));

        LexRespond respond = getCloseFulfilledLexRespond(request, builder);

        ResponseCard respondCard = getResponseCard();
        respond.getDialogAction().setResponseCard(respondCard);

        return respond;
    }

    private ResponseCard getResponseCard() {
        ResponseCard respondCard = new ResponseCard();
        respondCard.addAttachment()
                .withTitle("How would you like to proceed?")
                .withSubTitle("Example of a respond card")
                .withImageUrl("https://pub-imgs-8765786548765123.s3.amazonaws.com/book-shelf.png")
                .withAttachmentLinkUrl("https://github.com/satr/amazon-lex-alexa-getting-started")
                .withButton("Show Book Search Result", "Show the book search result")
                .withButton("Complete Order", "Complete the order");
        return respondCard;
    }
}
