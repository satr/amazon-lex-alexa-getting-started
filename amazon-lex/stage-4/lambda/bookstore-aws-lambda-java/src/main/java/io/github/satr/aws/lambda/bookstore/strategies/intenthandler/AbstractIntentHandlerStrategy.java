package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.DialogAction;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.respond.Message;

public abstract class AbstractIntentHandlerStrategy implements IntentHandlerStrategy {
    protected LexRespond getCloseFulfilledLexRespond(LexRequest request, StringBuilder messageBuilder) {
        return getCloseFulfilledLexRespond(request, messageBuilder.toString());
    }

    protected LexRespond getCloseFulfilledLexRespond(LexRequest request, String messageFormat, Object... args) {
        Message message = new Message(Message.ContentType.PlainText, String.format(messageFormat, args));
        DialogAction dialogAction = new DialogAction(DialogAction.Type.Close, DialogAction.FulfillmentState.Fulfilled, message);
        LexRespond respond = new LexRespond(dialogAction);
        respond.setSessionFrom(request);
        return respond;
    }
}
