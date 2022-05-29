package io.github.satr.aws.lambda.bookstore.repositories.database.tableentity;
// Copyright © 2022, github.com/satr, MIT License

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import io.github.satr.aws.lambda.bookstore.entity.Book;

@DynamoDBTable(tableName="BookSearchResult")
public class BookSearchResultItem extends Book {
    public BookSearchResultItem() {
    }

    public BookSearchResultItem(Book book) {
        copyFrom(book);
    }

    @DynamoDBHashKey(attributeName = "isbn")
    @Override
    public String getIsbn() {
        return super.getIsbn();
    }

    @DynamoDBAttribute(attributeName = "title")
    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @DynamoDBAttribute(attributeName = "author")
    @Override
    public String getAuthor() {
        return super.getAuthor();
    }

    @DynamoDBAttribute(attributeName = "issueYear")
    @Override
    public int getIssueYear() {
        return super.getIssueYear();
    }

    @DynamoDBAttribute(attributeName = "price")
    @Override
    public double getPrice() {
        return super.getPrice();
    }
}
