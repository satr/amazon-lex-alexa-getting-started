package io.github.satr.aws.lambda.bookstore.strategies.selectbook;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.common.OperationValueResult;
import io.github.satr.aws.lambda.bookstore.common.OperationValueResultImpl;
import io.github.satr.aws.lambda.bookstore.constants.SessionAttributeKey;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.response.Response;
import io.github.satr.aws.lambda.bookstore.response.Message;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;

public abstract class AbstractSelectBookStrategy implements SelectBookStrategy {
    protected final SearchBookResultService searchBookResultService;
    private OperationValueResult<Book> selectedBookResult = new OperationValueResultImpl<>();

    public AbstractSelectBookStrategy(SearchBookResultService searchBookResultService) {
        this.searchBookResultService = searchBookResultService;
        selectedBookResult.addError("Book has not been selected");
    }

    @Override
    public void selectBookByPositionInList(String positionInSequence) {
        selectedBookResult = searchBookResultService.getByPositionInSequence(positionInSequence);
    }

    @Override
    public void selectBookByNumberInSequence(Integer itemNumber) {
        selectedBookResult = searchBookResultService.getByNumberInSequence(itemNumber);
    }

    protected OperationValueResult<Book> getSelectedBookResult() {
        return selectedBookResult;
    }

    @Override
    public void processSelectedBook(Response respond) {
        Message message = respond.getDialogAction().getMessage();
        OperationValueResult<Book> selectedBookResult = getSelectedBookResult();
        if(!selectedBookResult.success() || selectedBookResult.getValue() == null) {
            message.setContentFormatted("Book is not selected:\n%s\nPlease try again", selectedBookResult.getErrorsAsString("\n"));
            return;
        }
        Book book = selectedBookResult.getValue();
        respond.getSessionAttributes().put(SessionAttributeKey.SelectedBookIsbn, book.getIsbn());
        processCustom(respond, book);
    }

    protected abstract void processCustom(Response respond, Book selectedBook);
}
