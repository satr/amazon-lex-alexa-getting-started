package io.github.satr.aws.lambda.bookstore.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.repositories.database.tableentity.BasketItem;
import io.github.satr.aws.lambda.bookstore.repositories.database.tableentity.BookSearchResultItem;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerBooksRepositoryImpl implements CustomerBooksRepository {
    private final DynamoDBMapper dbMapper;
    private List<Book> basketBooks = new LinkedList<>();

    public CustomerBooksRepositoryImpl(AmazonDynamoDB dynamoDbClient, DynamoDBMapper dbMapper) {
        this.dbMapper = dbMapper;
    }

    @Override
    public void addToBasket(Book book) {
        dbMapper.save(book);
    }

    @Override
    public void clearBasket() {
        PaginatedScanList<BasketItem> items = dbMapper.scan(BasketItem.class, new DynamoDBScanExpression());
        dbMapper.batchDelete(items);
    }

    @Override
    public void removeFromBasket(Book book) {
        Book bookInBasket = basketBooks.stream().filter(b -> book.getIsbn().equals(b.getIsbn())).findFirst().orElse(null);
        if(bookInBasket != null)
            dbMapper.delete(bookInBasket);
    }

    @Override
    public int getBasketSize() {
        return dbMapper.count(BasketItem.class, new DynamoDBScanExpression());
    }

    @Override
    public void putToBookSearchResult(List<Book> books) {
        dbMapper.save(books);
    }

    @Override
    public List<Book> getBasketBooks() {
        return dbMapper.scan(BasketItem.class, new DynamoDBScanExpression()).stream().collect(Collectors.toList());
    }

    @Override
    public List<Book> getBookSearchResult() {
        return dbMapper.scan(BookSearchResultItem.class, new DynamoDBScanExpression()).stream().collect(Collectors.toList());
    }
}
