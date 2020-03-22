package io.github.satr.aws.lambda.bookstore.response.responsecard;
// Copyright © 2020, github.com/satr, MIT License

public class Button {
    private final String text;
    private final String value;

    public Button(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
