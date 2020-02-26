package io.github.satr.aws.lambda.bookstore.strategies.selectbook;

import io.github.satr.aws.lambda.bookstore.common.OperationValueResult;
import io.github.satr.aws.lambda.bookstore.common.OperationValueResultImpl;
import io.github.satr.aws.lambda.bookstore.constants.SessionAttributeKey;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.respond.Message;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;

public abstract class AbstractSelectBookStrategy implements SelectBookStrategy {
    protected final BookStorageService bookStorageService;
    protected final FoundBookListService foundBookListService;
    private OperationValueResult<Book> selectedBookResult = new OperationValueResultImpl<>();

    public AbstractSelectBookStrategy(BookStorageService bookStorageService, FoundBookListService foundBookListService) {
        this.bookStorageService = bookStorageService;
        this.foundBookListService = foundBookListService;
        selectedBookResult.addError("Book has not been selected");
    }

    @Override
    public void selectBookByPositionInList(String positionInSequence) {
        selectedBookResult = foundBookListService.getByPositionInSequence(positionInSequence);
    }

    @Override
    public void selectBookByNumberInSequence(Integer itemNumber) {
        selectedBookResult = foundBookListService.getByNumberInSequence(itemNumber);
    }

    protected OperationValueResult<Book> getSelectedBookResult() {
        return selectedBookResult;
    }

    @Override
    public void process(LexRespond respond) {
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

    protected abstract void processCustom(LexRespond respond, Book selectedBook);
}
