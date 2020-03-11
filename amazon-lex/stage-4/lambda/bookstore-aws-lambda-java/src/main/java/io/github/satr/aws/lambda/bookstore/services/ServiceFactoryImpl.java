package io.github.satr.aws.lambda.bookstore.services;

import io.github.satr.aws.lambda.bookstore.repositories.CustomerBooksRepository;
import io.github.satr.aws.lambda.bookstore.repositories.database.DatabaseRepositoryFactory;

public class ServiceFactoryImpl implements ServiceFactory {

    private final SearchBookResultService searchBookResultService;
    private final BasketService basketService;
    private BookStorageService bookStorageService;

    public ServiceFactoryImpl(DatabaseRepositoryFactory databaseRepositoryFactory) {
        bookStorageService = new BookStorageServiceImpl();
        CustomerBooksRepository customerBooksRepository = databaseRepositoryFactory.getCustomerBooksRepository();
        searchBookResultService = new SearchBookResultServiceImpl(customerBooksRepository);
        basketService = new BasketServiceImpl(customerBooksRepository);
    }

    @Override
    public BookStorageService getBookStorageService() {
        return bookStorageService;
    }

    @Override
    public SearchBookResultService getSearchBookResultService() {
        return searchBookResultService;
    }

    @Override
    public BasketService getBasketService() {
        return basketService;
    }
}
