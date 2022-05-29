package io.github.satr.aws.lambda.bookstore.common;
// Copyright © 2022, github.com/satr, MIT License

import java.util.LinkedList;
import java.util.List;

public class OperationResultImpl implements OperationResult {
    private List<String> errors = new LinkedList<>();

    @Override
    public OperationResult addError(String format, Object... args) {
        errors.add(String.format(format, args));
        return this;
    }

    @Override
    public boolean success() {
        return errors.isEmpty();
    }

    @Override
    public List<String> getErrors() {
        return errors;
    }

    @Override
    public String getErrorsAsString(String delimiter) {
        return String.join(delimiter, errors);
    }
}
