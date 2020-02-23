package io.github.satr.aws.lambda.bookstore.common;

public interface OperationResult {
    OperationResult addError(String format, Object... args);
    boolean failed();
    boolean success();

    String getErrorsAsString(String itemSeparator);
}
