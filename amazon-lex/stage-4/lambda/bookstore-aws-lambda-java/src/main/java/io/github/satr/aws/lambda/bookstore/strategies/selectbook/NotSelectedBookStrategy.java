package io.github.satr.aws.lambda.bookstore.strategies.selectbook;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.respond.LexRespond;

public class NotSelectedBookStrategy implements SelectBookStrategy {
    @Override
    public void processSelectedBook(LexRespond respond) {
        respond.getDialogAction().getMessage().setContent("Book is not selected.");
    }

    @Override
    public void selectBookByPositionInList(String positionInSequence) {
    }

    @Override
    public void selectBookByNumberInSequence(Integer itemNumber) {
    }
}
