package io.github.satr.aws.lambda.bookstore.services;

import io.github.satr.aws.lambda.bookstore.entity.Basket;

public class BookOrderServiceImpl implements BookOrderService {

    private Basket basket;

    @Override
    public Basket getBasket() {
        basket = new Basket();
        return basket;
    }
}
