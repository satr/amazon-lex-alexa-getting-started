package io.github.satr.aws.lambda.bookstore.repositories.database;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.repositories.CustomerBooksRepository;

public interface DatabaseRepositoryFactory {
    CustomerBooksRepository getCustomerBooksRepository();
    void shutdown();
}
