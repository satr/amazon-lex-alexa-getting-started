package io.github.satr.aws.lambda.bookstore.common;
// Copyright © 2020, github.com/satr, MIT License

public class OperationValueResultImpl<T> extends OperationResultImpl implements OperationValueResult<T> {
    private T value;

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }
}
