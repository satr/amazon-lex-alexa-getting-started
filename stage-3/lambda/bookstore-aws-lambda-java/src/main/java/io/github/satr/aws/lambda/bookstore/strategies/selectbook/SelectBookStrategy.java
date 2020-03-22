package io.github.satr.aws.lambda.bookstore.strategies.selectbook;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.response.Response;

public interface SelectBookStrategy {
    void processSelectedBook(Response response);
    void selectBookByPositionInList(String positionInSequence);
    void selectBookByNumberInSequence(Integer itemNumber);
}
