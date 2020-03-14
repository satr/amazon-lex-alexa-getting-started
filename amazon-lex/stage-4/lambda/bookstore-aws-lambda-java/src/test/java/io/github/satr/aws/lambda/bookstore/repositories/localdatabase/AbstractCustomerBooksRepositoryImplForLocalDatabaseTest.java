package io.github.satr.aws.lambda.bookstore.repositories.localdatabase;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import io.github.satr.aws.lambda.bookstore.repositories.CustomerBooksRepositoryImpl;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class AbstractCustomerBooksRepositoryImplForLocalDatabaseTest {
    protected static AmazonDynamoDB dynamoDbClient;
    private static DynamoDBMapper dbMapper;
    protected CustomerBooksRepositoryImpl repository;

    @BeforeClass
    public static void fixtureSetUp() throws Exception {
        dynamoDbClient = ObjectMother.createInMemoryDb();
        dbMapper = new DynamoDBMapper(dynamoDbClient);
    }

    @AfterClass
    public static void fixtureTearDown() throws Exception {
        if(dynamoDbClient != null)
            dynamoDbClient.shutdown();
    }

    @Before
    public void setUp() throws Exception {
        repository = new CustomerBooksRepositoryImpl(dbMapper);
        customSetUp();
    }

    protected abstract void customSetUp();
}
