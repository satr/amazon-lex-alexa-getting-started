package io.github.satr.aws.lambda.bookstore.repositories.database.tableentity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import io.github.satr.aws.lambda.bookstore.entity.Book;

@DynamoDBTable(tableName="BookSearchResult")
public class BookSearchResultItem extends Book {
}
