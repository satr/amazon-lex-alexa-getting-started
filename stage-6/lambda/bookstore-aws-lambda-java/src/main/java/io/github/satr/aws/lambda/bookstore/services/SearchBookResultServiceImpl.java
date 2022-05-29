package io.github.satr.aws.lambda.bookstore.services;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.common.OperationValueResult;
import io.github.satr.aws.lambda.bookstore.common.OperationValueResultImpl;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotValue;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.repositories.CustomerBooksRepository;

import java.util.List;

public class SearchBookResultServiceImpl implements SearchBookResultService {
    private final CustomerBooksRepository customerBooksRepository;

    public SearchBookResultServiceImpl(CustomerBooksRepository customerBooksRepository) {
        this.customerBooksRepository = customerBooksRepository;
    }

    @Override
    public void put(List<Book> books) {
        customerBooksRepository.clearBookSearchResult();
        customerBooksRepository.putToBookSearchResult(books);
    }

    @Override
    public OperationValueResult<Book> getByPositionInSequence(String positionInSequence) {
        List<Book> books = getResult();
        OperationValueResult<Book> result = validate(books);
        if (!result.success())
            return result;
        Integer itemNumber = IntentSlotValue.PositionInSequence.getNumberInSequenceByPosition(positionInSequence, books.size());
        tryGetByNumberInSequence(result, itemNumber);
        return result;
    }

    @Override
    public OperationValueResult<Book> getByNumberInSequence(Integer itemNumber) {
        OperationValueResult<Book> result = validate(getResult());
        if (!result.success())
            return result;
        tryGetByNumberInSequence(result, itemNumber);
        return result;
    }

    @Override
    public OperationValueResult<Book> getByIsbn(String isbn) {
        Book book = getResult().stream().filter(b -> b.getIsbn().equals(isbn)).findFirst().orElse(null);
        OperationValueResultImpl<Book> result = new OperationValueResultImpl<>();
        if(book != null)
            result.setValue(book);
        else
            result.addError("Book not found by selected ISBN: %s.", isbn);
        return result;
    }

    @Override
    public List<Book> getResult() {
        return customerBooksRepository.getBookSearchResult();
    }

    private void tryGetByNumberInSequence(OperationValueResult<Book> result, Integer itemNumber) {
        List<Book> books = getResult();
        int resultCount = books.size();
        if (itemNumber == null || itemNumber <= 0) {
            result.addError("Cannot identify a book in the list by item number %s. Please try again.", itemNumber);
            return;
        }
        if(resultCount < itemNumber)
        {
            result.addError("Book search result contains only %d books. Please try again.", resultCount);
            return;
        }
        result.setValue(books.get(itemNumber - 1));
    }

    private OperationValueResult<Book> validate(List<Book> bookList) {
        OperationValueResult<Book> result = new OperationValueResultImpl<>();
        if(bookList.isEmpty())
            result.addError("Book search result is empty.");
        return result;
    }
}
