package io.github.satr.aws.lambda.bookstore.repositories.database;

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

    public DatabaseRepositoryFactoryImpl() {
        dynamoDbClient = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        dbMapper = new DynamoDBMapper(dynamoDbClient);
    }

    @Override
    public CustomerBooksRepository getCustomerBooksRepository() {
        return customerBooksRepository != null ? customerBooksRepository : (customerBooksRepository = new CustomerBooksRepositoryImpl(dynamoDbClient, dbMapper));
    }

    @Override
    public void shutdown() {
        customerBooksRepository = null;
        dynamoDbClient.shutdown();
    }
}
