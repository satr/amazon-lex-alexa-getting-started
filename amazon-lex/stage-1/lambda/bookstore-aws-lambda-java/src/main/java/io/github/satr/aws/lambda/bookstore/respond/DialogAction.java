package io.github.satr.aws.lambda.bookstore.respond;
// Copyright © 2020, github.com/satr, MIT License

public class DialogAction {
    private String type;
    private String fulfillmentState;
    private Message message;

    public final class Type {
        public static final String PlainText = "PlainText";
        public static final String ElicitSlot = "ElicitSlot";
        public static final String Close = "Close";
    }

    public class FulfillmentState {
        public static final String Fulfilled = "Fulfilled";
        public static final String Failed = "Failed";
    }

    public DialogAction(String type, String fulfillmentState, Message message) {

        this.type = type;
        this.fulfillmentState = fulfillmentState;
        this.message = message;
    }

    public DialogAction() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFulfillmentState() {
        return fulfillmentState;
    }

    public void setFulfillmentState(String fulfillmentState) {
        this.fulfillmentState = fulfillmentState;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
