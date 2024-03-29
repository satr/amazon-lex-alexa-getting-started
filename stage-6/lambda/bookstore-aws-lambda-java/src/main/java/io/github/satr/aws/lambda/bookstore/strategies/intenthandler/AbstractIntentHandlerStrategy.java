package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.entity.formatter.MessageFormatter;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.respond.DialogAction;
import io.github.satr.aws.lambda.bookstore.respond.Response;
import io.github.satr.aws.lambda.bookstore.respond.Message;

public abstract class AbstractIntentHandlerStrategy implements IntentHandlerStrategy {
    protected MessageFormatter messageFormatter;

    public AbstractIntentHandlerStrategy(MessageFormatter messageFormatter) {
        this.messageFormatter = messageFormatter;
    }

    protected Response getCloseFulfilledLexRespond(Request request, StringBuilder messageBuilder) {
        return getCloseFulfilledLexRespond(request, messageBuilder.toString());
    }

    protected Response getCloseFulfilledLexRespond(Request request, String messageFormat, Object... args) {
        Message message = new Message(Message.ContentType.PlainText, String.format(messageFormat, args));
        DialogAction dialogAction = new DialogAction(DialogAction.Type.Close, DialogAction.FulfillmentState.Fulfilled, message);
        Response respond = new Response(dialogAction);
        respond.setSessionFrom(request);
        return respond;
    }
}
