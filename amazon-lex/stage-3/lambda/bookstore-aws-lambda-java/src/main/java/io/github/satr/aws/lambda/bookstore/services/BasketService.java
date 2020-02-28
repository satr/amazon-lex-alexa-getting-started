package io.github.satr.aws.lambda.bookstore.services;

import io.github.satr.aws.lambda.bookstore.common.OperationResult;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import java.util.List;

public interface BasketService {
    List<Book> getBooks();
    Book getBookByIsbn(String isbn);
    OperationResult add(Book book);
    void clearBasket();
}
