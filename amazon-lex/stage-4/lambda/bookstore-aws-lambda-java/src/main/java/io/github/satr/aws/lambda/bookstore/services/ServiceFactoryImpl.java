package io.github.satr.aws.lambda.bookstore.services;

import io.github.satr.aws.lambda.bookstore.repositories.CustomerBooksRepository;
import io.github.satr.aws.lambda.bookstore.repositories.CustomerBooksRepositoryImpl;

public class ServiceFactoryImpl implements ServiceFactory {

    private final SearchBookResultService searchBookResultService;
    private final BasketService basketService;
    private BookStorageService bookStorageService;

    public ServiceFactoryImpl() {
        bookStorageService = new BookStorageServiceImpl();
        CustomerBooksRepository customerBooksRepository = new CustomerBooksRepositoryImpl();
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
