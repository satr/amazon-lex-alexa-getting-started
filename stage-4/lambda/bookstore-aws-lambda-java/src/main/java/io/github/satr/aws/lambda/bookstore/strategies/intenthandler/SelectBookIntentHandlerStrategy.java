package io.github.satr.aws.lambda.bookstore.strategies.intenthandler;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotValue;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.response.Response;
import io.github.satr.aws.lambda.bookstore.services.BasketService;
import io.github.satr.aws.lambda.bookstore.services.SearchBookResultService;
import io.github.satr.aws.lambda.bookstore.strategies.selectbook.AddBookToBasketStrategy;
import io.github.satr.aws.lambda.bookstore.strategies.selectbook.SelectBookStrategy;
import io.github.satr.aws.lambda.bookstore.strategies.selectbook.ShowBookDetailsStrategy;
import java.util.HashMap;
import java.util.Map;

public class SelectBookIntentHandlerStrategy extends AbstractSelectBookIntentHandlerStrategy {
    private Map<String, SelectBookStrategy> selectBookStrategies = new HashMap<>();

    public SelectBookIntentHandlerStrategy(SearchBookResultService searchBookResultService, BasketService basketService) {
        selectBookStrategies.put(IntentSlotValue.ChooseFromListAction.Show, new ShowBookDetailsStrategy(searchBookResultService));
        selectBookStrategies.put(IntentSlotValue.ChooseFromListAction.Order, new AddBookToBasketStrategy(searchBookResultService, basketService));
    }

    @Override
    protected Response customHandle(Request request, Response respond, String itemNumber, String positionInSequence) {
        String chooseFromListAction = request.getSlot(IntentSlotName.ChooseFromListAction);
        SelectBookStrategy selectBookStrategy = selectBookStrategies.get(chooseFromListAction);

        selectBookBy(selectBookStrategy, itemNumber, positionInSequence)
                .processSelectedBook(respond);

        return respond;
    }
}
