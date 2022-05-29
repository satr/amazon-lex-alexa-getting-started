package io.github.satr.aws.lambda.bookstore.repositories;
// Copyright © 2022, github.com/satr, MIT License

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.repositories.database.tableentity.BasketItem;
import io.github.satr.aws.lambda.bookstore.repositories.database.tableentity.BookSearchResultItem;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerBooksRepositoryImpl extends AbstractRepository implements CustomerBooksRepository {
    public CustomerBooksRepositoryImpl(DynamoDBMapper dbMapper) {
        super(dbMapper);
    }

    @Override
    public void addToBasket(Book book) {
        dbMapper.save(new BasketItem(book));
    }

    @Override
    public void clearBasket() {
        PaginatedScanList<BasketItem> items = dbMapper.scan(BasketItem.class, new DynamoDBScanExpression());
        dbMapper.batchDelete(items);
    }

    @Override
    public void removeFromBasket(Book book) {
        Book bookInBasket = scan(BasketItem.class, "isbn", book.getIsbn()).stream().findFirst().orElse(null);
        if(bookInBasket != null)
            dbMapper.delete(bookInBasket);
    }

    @Override
    public int getBasketSize() {
        return dbMapper.count(BasketItem.class, new DynamoDBScanExpression());
    }

    @Override
    public List<Book> getBasketBooks() {
        return dbMapper.scan(BasketItem.class, new DynamoDBScanExpression()).stream().collect(Collectors.toList());
    }

    @Override
    public List<Book> getBookSearchResult() {
        return dbMapper.scan(BookSearchResultItem.class, new DynamoDBScanExpression()).stream().collect(Collectors.toList());
    }

    @Override
    public void putToBookSearchResult(List<? extends Book> books) {
        dbMapper.batchSave(books.stream().map(b -> new BookSearchResultItem(b)).collect(Collectors.toList()));
    }

    @Override
    public void clearBookSearchResult() {
        PaginatedScanList<BookSearchResultItem> items = dbMapper.scan(BookSearchResultItem.class, new DynamoDBScanExpression());
        dbMapper.batchDelete(items);
    }
}
