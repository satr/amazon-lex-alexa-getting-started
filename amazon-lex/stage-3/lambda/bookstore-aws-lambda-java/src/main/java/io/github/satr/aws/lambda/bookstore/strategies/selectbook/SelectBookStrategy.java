package io.github.satr.aws.lambda.bookstore.strategies.selectbook;

import io.github.satr.aws.lambda.bookstore.respond.LexRespond;

public interface SelectBookStrategy {
    void process(LexRespond respond);
    void selectBookByPositionInList(String positionInSequence);
    void selectBookByNumberInSequence(Integer itemNumber);
}
