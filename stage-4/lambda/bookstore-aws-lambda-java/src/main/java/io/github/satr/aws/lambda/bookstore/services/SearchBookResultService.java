package io.github.satr.aws.lambda.bookstore.services;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.common.OperationValueResult;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import java.util.List;

public interface SearchBookResultService {
    void put(List<Book> searchBookResultList);
    OperationValueResult<Book> getByPositionInSequence(String positionInSequence);
    OperationValueResult<Book> getByNumberInSequence(Integer itemNumber);
    List<Book> getResult();
}
