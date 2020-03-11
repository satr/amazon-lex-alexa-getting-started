package io.github.satr.aws.lambda.bookstore.repositories.realconnection;

import io.github.satr.aws.lambda.bookstore.repositories.CustomerBooksRepository;
import io.github.satr.aws.lambda.bookstore.repositories.database.DatabaseRepositoryFactory;
import io.github.satr.aws.lambda.bookstore.repositories.database.DatabaseRepositoryFactoryImpl;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class AbstractCustomerBooksRepositoryImplRealConnectionTest {
    private static DatabaseRepositoryFactory repositoryFactory;
    protected CustomerBooksRepository repository;

    @BeforeClass
    public static void fixtureSetUp() throws Exception {
        repositoryFactory = new DatabaseRepositoryFactoryImpl();
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
