package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.entity.formatter.MessageFormatter;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.respond.Response;
import io.github.satr.aws.lambda.bookstore.respond.responsecard.ResponseCard;
import io.github.satr.aws.lambda.bookstore.services.BasketService;

import java.util.List;

// Copyright © 2022, github.com/satr, MIT License

public class ShowBasketIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    private final BasketService basketService;

    public ShowBasketIntentHandlerStrategy(BasketService basketService, MessageFormatter messageFormatter) {
        super(messageFormatter);
        this.basketService = basketService;
    }

    @Override
    public Response handle(Request request, LambdaLogger logger) {
        List<Book> booksInBasket = basketService.getBooks();

        if (booksInBasket.isEmpty())
            return getCloseFulfilledLexRespond(request, "Basket is empty.");

        StringBuilder builder = new StringBuilder(messageFormatter.getBookShortDescriptionListWithPrices(booksInBasket,
                "Basket contains %s:\n", messageFormatter.amountOfBooks(booksInBasket.size())));
        Double totalPrice = booksInBasket.stream().map(Book::getPrice).reduce(Double::sum).get();
        builder.append(messageFormatter.getShortBreak());
        builder.append(String.format("Total: %s", messageFormatter.getPriceText(totalPrice)));

        Response respond = getCloseFulfilledLexRespond(request, builder);

        ResponseCard respondCard = getResponseCard();
        respond.getDialogAction().setResponseCard(respondCard);

        return respond;
    }

    private ResponseCard getResponseCard() {
        ResponseCard respondCard = new ResponseCard();
        respondCard.addAttachment()
                .withTitle("How would you like to proceed?")
                .withSubTitle("Example of a respond card")
                .withImageUrl("https://pub-imgs-8765786548765123.s3.amazonaws.com/book-shelf-300x200.png")
                .withAttachmentLinkUrl("https://github.com/satr/amazon-lex-alexa-getting-started")
                .withButton("Show Book Search Result", "Show the book search result")
                .withButton("Complete Order", "Complete the order");
        return respondCard;
    }
}
