package io.github.satr.aws.lambda.bookstore.strategies.selectbook;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.respond.Response;

public interface SelectBookStrategy {
    void processSelectedBook(Response respond);
    void selectBookByPositionInList(String positionInSequence);
    void selectBookByNumberInSequence(Integer itemNumber);
    void selectBookByIsbn(String bookIsbn);
}
