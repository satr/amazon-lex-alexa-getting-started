package io.github.satr.aws.lambda.bookstore.repositories.localdatabase;
// Copyright © 2022, github.com/satr, MIT License

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import io.github.satr.aws.lambda.bookstore.repositories.database.Table;

import java.util.ArrayList;
import java.util.List;


public final class LocalDatabaseTableHelper {

    public static void deleteTableBasket(AmazonDynamoDB dynamoDbClient) {
        dynamoDbClient.deleteTable(Table.Basket.Name);
    }

    public static void createTableBasket(AmazonDynamoDB dynamoDb) {
        createTable(dynamoDb, Table.Basket.Name, Table.Basket.Attr.Isbn);
    }

    public static void deleteTableBookSearchResult(AmazonDynamoDB dynamoDbClient) {
        dynamoDbClient.deleteTable(Table.BookSearchResult.Name);
    }

    public static void createTableBookSearchResult(AmazonDynamoDB dynamoDb) {
        createTable(dynamoDb, Table.BookSearchResult.Name, Table.BookSearchResult.Attr.Isbn);
    }

    private static void createTable(AmazonDynamoDB dynamodb, String tableName, String tableKeyFieldName) {
        List<AttributeDefinition> attributeDefinitions= new ArrayList<>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName(tableKeyFieldName).withAttributeType("S"));

        List<KeySchemaElement> keySchema = new ArrayList<>();
        keySchema.add(new KeySchemaElement().withAttributeName(tableKeyFieldName).withKeyType(KeyType.HASH));

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(keySchema)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(1L)
                        .withWriteCapacityUnits(1L));

        dynamodb.createTable(request);
        // Uncomment following like - just to look at the result, if needed:
        // TableDescription table = dynamodb.describeTable(tableName).getTable();
    }
}
