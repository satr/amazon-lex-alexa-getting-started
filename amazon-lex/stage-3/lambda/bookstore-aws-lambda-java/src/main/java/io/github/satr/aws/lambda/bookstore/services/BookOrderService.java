package io.github.satr.aws.lambda.bookstore.services;

import io.github.satr.aws.lambda.bookstore.entity.Basket;

public interface BookOrderService {
    Basket getBasket();
}
