package io.github.satr.aws.lambda.bookstore.repositories.clouddatabase;
// Copyright © 2022, github.com/satr, MIT License

import com.amazonaws.regions.Regions;
import io.github.satr.aws.lambda.bookstore.repositories.CustomerBooksRepository;
import io.github.satr.aws.lambda.bookstore.repositories.database.DatabaseRepositoryFactory;
import io.github.satr.aws.lambda.bookstore.repositories.database.DatabaseRepositoryFactoryImpl;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class AbstractCustomerBooksRepositoryImplForCloudDatabaseTest {
    private static DatabaseRepositoryFactory repositoryFactory;
    protected CustomerBooksRepository repository;

    @BeforeClass
    public static void fixtureSetUp() throws Exception {
        repositoryFactory = new DatabaseRepositoryFactoryImpl(Regions.US_EAST_1);
    }

    @AfterClass
    public static void fixtureTearDown() throws Exception {
        repositoryFactory.shutdown();
    }

    @Before
    public void setUp() throws Exception {
        repository = repositoryFactory.getCustomerBooksRepository();
    }
}
