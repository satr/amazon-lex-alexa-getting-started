package io.github.satr.aws.lambda.bookstore.common;
// Copyright © 2020, github.com/satr, MIT License

import java.util.List;

public interface OperationResult {
    OperationResult addError(String format, Object... args);
    boolean success();
    List<String> getErrors();
    String getErrorsAsString(String itemSeparator);
}
