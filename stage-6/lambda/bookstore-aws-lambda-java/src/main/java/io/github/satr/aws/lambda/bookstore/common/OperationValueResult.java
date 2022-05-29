package io.github.satr.aws.lambda.bookstore.common;
// Copyright © 2022, github.com/satr, MIT License

public interface OperationValueResult<T> extends OperationResult {
    T getValue();
    void setValue(T value);
}
