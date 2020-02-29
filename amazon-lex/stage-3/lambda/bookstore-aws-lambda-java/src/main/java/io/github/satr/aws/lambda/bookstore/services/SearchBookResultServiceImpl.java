package io.github.satr.aws.lambda.bookstore.services;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.common.OperationValueResult;
import io.github.satr.aws.lambda.bookstore.common.OperationValueResultImpl;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotValue;
import io.github.satr.aws.lambda.bookstore.entity.Book;

import java.util.LinkedList;
import java.util.List;

public class SearchBookResultServiceImpl implements SearchBookResultService {
    private List<Book> bookList = new LinkedList<>();

    @Override
    public void put(List<Book> searchBookResultList) {
        this.bookList = searchBookResultList;
    }

    @Override
    public OperationValueResult<Book> getByPositionInSequence(String positionInSequence) {
        OperationValueResult<Book> result = validate(bookList);
        if (!result.success())
            return result;
        Integer itemNumber = IntentSlotValue.PositionInSequence.getNumberInSequenceByPosition(positionInSequence, bookList.size());
        tryGetByNumberInSequence(result, itemNumber);
        return result;
    }

    @Override
    public OperationValueResult<Book> getByNumberInSequence(Integer itemNumber) {
        OperationValueResult<Book> result = validate(bookList);
        if (!result.success())
            return result;
        tryGetByNumberInSequence(result, itemNumber);
        return result;
    }

    @Override
    public List<Book> getList() {
        return bookList;
    }

    private void tryGetByNumberInSequence(OperationValueResult<Book> result, Integer itemNumber) {
        int resultCount = bookList.size();
        if (itemNumber == null || itemNumber <= 0) {
            result.addError("Cannot identify a book in the list by item number %s. Please try again.", itemNumber);
            return;
        }
        if(resultCount < itemNumber)
        {
            result.addError("Book search result contains only %d books. Please try again.", resultCount);
            return;
        }
        result.setValue(bookList.get(itemNumber - 1));
    }

    private OperationValueResult<Book> validate(List<Book> bookList) {
        OperationValueResult<Book> result = new OperationValueResultImpl<>();
        if(bookList.isEmpty())
            result.addError("Book search result is empty.");
        return result;
    }
}
