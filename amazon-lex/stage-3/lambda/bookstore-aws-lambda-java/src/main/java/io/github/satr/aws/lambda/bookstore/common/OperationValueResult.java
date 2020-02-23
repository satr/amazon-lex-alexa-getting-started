package io.github.satr.aws.lambda.bookstore.common;

public interface OperationValueResult<T> extends OperationResult {
    T getValue();
    void setValue(T value);
}
