package io.github.satr.aws.lambda.bookstore.services;

public interface ServiceFactory {
    BookStorageService getBookStorageService();
    SearchBookResultService getSearchBookResultService();
    BasketService getBasketService();
}
