package io.github.satr.aws.lambda.bookstore.common;

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
    public boolean failed() {
        return !success();
    }

    @Override
    public boolean success() {
        return errors.isEmpty();
    }

    @Override
    public String getErrorsAsString(String delimiter) {
        return String.join(delimiter, errors);
    }
}
