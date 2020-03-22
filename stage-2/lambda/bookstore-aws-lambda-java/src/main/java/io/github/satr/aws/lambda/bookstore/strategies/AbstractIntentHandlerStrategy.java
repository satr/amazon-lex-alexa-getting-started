package io.github.satr.aws.lambda.bookstore.strategies;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.response.DialogAction;
import io.github.satr.aws.lambda.bookstore.response.Response;
import io.github.satr.aws.lambda.bookstore.response.Message;

public abstract class AbstractIntentHandlerStrategy implements IntentHandlerStrategy {
    protected Response getCloseFulfilledLexRespond(String messageFormat, Object... args) {
        Message message = new Message(Message.ContentType.PlainText, String.format(messageFormat, args));
        DialogAction dialogAction = new DialogAction(DialogAction.Type.Close, DialogAction.FulfillmentState.Fulfilled, message);
        return new Response(dialogAction);
    }
}
