package io.github.satr.aws.lambda.bookstore.repositories;

import io.github.satr.aws.lambda.bookstore.entity.Book;

import java.util.LinkedList;
import java.util.List;

public class CustomerBooksRepositoryImpl implements CustomerBooksRepository {
    private List<Book> basketBooks = new LinkedList<>();
    private List<Book> bookSearchResult = new LinkedList<>();

    @Override
    public void addToBasket(Book book) {
        basketBooks.add(book);
    }

    @Override
    public void clearBasket() {
        basketBooks.clear();
    }

    @Override
    public void removeFromBasket(Book book) {
        Book bookInBasket = basketBooks.stream().filter(b -> book.getIsbn().equals(b.getIsbn())).findFirst().orElse(null);
        if(bookInBasket != null)
            basketBooks.remove(bookInBasket);
    }

    @Override
    public int getBasketSize() {
        return basketBooks.size();
    }

    private Book getB(String isbn) {
        for(Book book : basketBooks)
            if(isbn.equals(book.getIsbn()))
                return book;
        return null;
    }

    @Override
    public void putToBookSearchResult(List<Book> books) {
        bookSearchResult = books;
    }

    @Override
    public List<Book> getBasketBooks() {
        return basketBooks;
    }

    @Override
    public List<Book> getBookSearchResult() {
        return bookSearchResult;
    }
}
