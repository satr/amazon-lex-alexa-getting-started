package io.github.satr.aws.lambda.bookstore.services;

import io.github.satr.aws.lambda.bookstore.common.OperationValueResult;
import io.github.satr.aws.lambda.bookstore.common.OperationValueResultImpl;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotValue;
import io.github.satr.aws.lambda.bookstore.entity.Book;

import java.util.LinkedList;
import java.util.List;

public class FoundBookListServiceImpl implements FoundBookListService {
    private List<Book> foundBookList = new LinkedList<>();

    @Override
    public void put(List<Book> foundBookList) {
        this.foundBookList = foundBookList;
    }

    @Override
    public OperationValueResult<Book> getByPositionInSequence(String positionInSequence) {
        OperationValueResult<Book> result = validate();
        if (result.failed())
            return result;
        Integer itemNumber = IntentSlotValue.PositionInSequence.getNumberInSequenceByPosition(positionInSequence, foundBookList.size());
        tryGetByNumberInSequence(result, itemNumber);
        return result;
    }

    @Override
    public OperationValueResult<Book> getByNumberInSequence(Integer itemNumber) {
        OperationValueResult<Book> result = validate();
        if (result.failed())
            return result;
        tryGetByNumberInSequence(result, itemNumber);
        return result;
    }

    private void tryGetByNumberInSequence(OperationValueResult<Book> result, Integer itemNumber) {
        if (itemNumber == null || itemNumber <= 0 || itemNumber > foundBookList.size()) {
            result.addError("Cannot identify a book in the list by item number %s.", itemNumber);
            return;
        }
        result.setValue(foundBookList.get(itemNumber - 1));
    }

    private OperationValueResult<Book> validate() {
        OperationValueResult<Book> result = new OperationValueResultImpl<>();
        if(foundBookList.isEmpty())
            result.addError("There are no books in the found book list.");
        return result;
    }
}
