package io.github.satr.aws.lambda.bookstore.request;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class RequestFactoryTest {

    @Test
    public void createFromOrderBookIntent() {
        Map<String, Object> input = ObjectMother.createMapFromJson("simple-order-book-intent-request.json");

        Request request = RequestFactory.createFrom(input);

        assertNotNull(request);
        assertNotNull(request.getSessionAttributes());
        assertNotNull(request.getSlots());
        assertEquals("BotName", request.getBotName());
        assertEquals(InvocationSource.DialogCodeHook, request.getInvocationSource());
        assertEquals("user-id-as-some-random-character-set", request.getUserId());
        assertEquals("OrderBookIntent", request.getIntentName());
        assertFalse(request.getSlots().isEmpty());
        assertNotNull("Some Book Title", request.getSlots().get(IntentSlotName.BookTitle));
        assertNotNull("Some Author", request.getSlots().get(IntentSlotName.BookAuthor));
        assertFalse(request.getSessionAttributes().isEmpty());
        assertNotNull("SessionVal1", request.getSessionAttributes().get("attr1"));
        assertNotNull("SessionVal2", request.getSessionAttributes().get("attr2"));
    }
}