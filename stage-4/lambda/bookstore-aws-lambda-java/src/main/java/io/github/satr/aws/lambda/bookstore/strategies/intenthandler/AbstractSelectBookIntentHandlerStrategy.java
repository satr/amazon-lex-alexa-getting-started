package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.response.Response;
import io.github.satr.aws.lambda.bookstore.strategies.selectbook.NotSelectedBookStrategy;
import io.github.satr.aws.lambda.bookstore.strategies.selectbook.SelectBookStrategy;

public abstract class AbstractSelectBookIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    protected SelectBookStrategy notSelectedBookStrategy = new NotSelectedBookStrategy();;

    @Override
    public Response handle(Request request, LambdaLogger logger) {
        String itemNumber = request.getSlot(IntentSlotName.ItemNumber);
        String positionInSequence = request.getSlot(IntentSlotName.PositionInSequence);
        return customHandle(request, getCloseFulfilledLexRespond(request, "Undefined message"), itemNumber, positionInSequence);
    }

    protected abstract Response customHandle(Request request, Response respond, String itemNumber, String positionInSequence);

    protected SelectBookStrategy selectBookBy(SelectBookStrategy selectBookStrategy, String itemNumber, String positionInSequence) {
        Integer itemNumberParsed;
        if (itemNumber != null && (itemNumberParsed = Integer.valueOf(itemNumber)) != null && itemNumberParsed > 0) {
            selectBookStrategy.selectBookByNumberInSequence(itemNumberParsed);
            return selectBookStrategy;
        }
        if(positionInSequence != null) {
            selectBookStrategy.selectBookByPositionInList(positionInSequence);
            return selectBookStrategy;
        }
        return notSelectedBookStrategy;
    }
}
