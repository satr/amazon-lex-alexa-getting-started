package io.github.satr.aws.lambda.bookstore.repositories.database;
// Copyright © 2022, github.com/satr, MIT License

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import io.github.satr.aws.lambda.bookstore.repositories.CustomerBooksRepository;
import io.github.satr.aws.lambda.bookstore.repositories.CustomerBooksRepositoryImpl;

public class DatabaseRepositoryFactoryImpl implements DatabaseRepositoryFactory {
    private final AmazonDynamoDB dynamoDbClient;
    private final DynamoDBMapper dbMapper;
    private CustomerBooksRepositoryImpl customerBooksRepository;

    public DatabaseRepositoryFactoryImpl(Regions region) {
        dynamoDbClient = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
        dbMapper = new DynamoDBMapper(dynamoDbClient);
    }

    @Override
    public CustomerBooksRepository getCustomerBooksRepository() {
        return customerBooksRepository != null ? customerBooksRepository : (customerBooksRepository = new CustomerBooksRepositoryImpl(dbMapper));
    }

    @Override
    public void shutdown() {
        customerBooksRepository = null;
        dynamoDbClient.shutdown();
    }
}
