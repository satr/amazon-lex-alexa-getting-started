package io.github.satr.aws.lambda.bookstore.repositories.database;

import io.github.satr.aws.lambda.bookstore.repositories.CustomerBooksRepository;

public interface DatabaseRepositoryFactory {
    CustomerBooksRepository getCustomerBooksRepository();
    void shutdown();
}
