package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotValue;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.respond.LexRespond;
import io.github.satr.aws.lambda.bookstore.services.BookOrderService;
import io.github.satr.aws.lambda.bookstore.services.BookStorageService;
import io.github.satr.aws.lambda.bookstore.services.FoundBookListService;
import io.github.satr.aws.lambda.bookstore.strategies.selectbook.NotSelectedBookStrategy;
import io.github.satr.aws.lambda.bookstore.strategies.selectbook.OrderBookStrategy;
import io.github.satr.aws.lambda.bookstore.strategies.selectbook.SelectBookStrategy;
import io.github.satr.aws.lambda.bookstore.strategies.selectbook.ShowBookDetailsStrategy;

import java.util.HashMap;
import java.util.Map;

public class SelectBookIntentHandlerStrategy extends AbstractIntentHandlerStrategy {
    private Map<String, SelectBookStrategy> selectBookStrategies = new HashMap<>();
    private NotSelectedBookStrategy notSelectedBookStrategy;

    public SelectBookIntentHandlerStrategy(BookStorageService bookStorageService, FoundBookListService foundBookListService, BookOrderService bookOrderService) {
        selectBookStrategies.put(IntentSlotValue.ChooseFromListAction.Show, new ShowBookDetailsStrategy(bookStorageService, foundBookListService));
        selectBookStrategies.put(IntentSlotValue.ChooseFromListAction.Order, new OrderBookStrategy(bookStorageService, foundBookListService, bookOrderService));
        notSelectedBookStrategy = new NotSelectedBookStrategy(bookStorageService, foundBookListService);
    }

    @Override
    public LexRespond handle(LexRequest request, LambdaLogger logger) {
        String itemNumber = request.getSlot(IntentSlotName.ItemNumber);
        String positionInSequence = request.getSlot(IntentSlotName.PositionInSequence);
        String chooseFromListAction = request.getSlot(IntentSlotName.ChooseFromListAction);

        LexRespond respond = getCloseFulfilledLexRespond(request, "Undefined message");

        getSelectBookStrategyBy(itemNumber, positionInSequence, chooseFromListAction)
                .process(respond);

        return respond;
    }

    private SelectBookStrategy getSelectBookStrategyBy(String itemNumber, String positionInSequence, String chooseFromListAction) {
        SelectBookStrategy selectBookStrategy = selectBookStrategies.get(chooseFromListAction);
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
