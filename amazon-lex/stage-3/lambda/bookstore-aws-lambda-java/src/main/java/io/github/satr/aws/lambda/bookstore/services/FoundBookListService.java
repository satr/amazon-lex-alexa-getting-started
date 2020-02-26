package io.github.satr.aws.lambda.bookstore.services;

import io.github.satr.aws.lambda.bookstore.common.OperationValueResult;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import java.util.List;

public interface FoundBookListService {
    void put(List<Book> foundBookList);
    OperationValueResult<Book> getByPositionInSequence(String positionInSequence);
    OperationValueResult<Book> getByNumberInSequence(Integer itemNumber);
    List<Book> getList();
}
