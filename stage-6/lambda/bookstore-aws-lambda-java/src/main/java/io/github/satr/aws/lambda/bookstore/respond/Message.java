package io.github.satr.aws.lambda.bookstore.respond;
// Copyright © 2022, github.com/satr, MIT License

public class Message {
    private String contentType;
    private String content;

    public class ContentType{
        public static final String PlainText = "PlainText";
        public static final String SSML = "SSML";
        public static final String CustomPayload = "CustomPayload";
        public static final String Composite = "Composite";
    }

    public Message(String contentType, String content) {
        this.contentType = contentType;
        this.content = content;
    }

    public Message() {
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public void setContent(StringBuilder builder) {
        setContent(builder.toString());
    }
    public void setContentFormatted(String format, Object... args) {
        setContent(String.format(format, args));
    }
}