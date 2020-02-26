package io.github.satr.aws.lambda.bookstore.request;

import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class LexRequestFactoryTest {

    @Test
    public void createFromOrderBookIntent() {
        Map<String, Object> input = ObjectMother.createMapFromJson("simple-order-book-intent-request.json");

        LexRequest lexRequest = LexRequestFactory.createFrom(input);

        assertNotNull(lexRequest);
        assertNotNull(lexRequest.getSessionAttributes());
        assertNotNull(lexRequest.getSlots());
        assertEquals("BotName", lexRequest.getBotName());
        assertEquals(InvocationSource.DialogCodeHook, lexRequest.getInvocationSource());
        assertEquals("user-id-as-some-random-character-set", lexRequest.getUserId());
        assertEquals("OrderBookIntent", lexRequest.getIntentName());
        assertFalse(lexRequest.getSlots().isEmpty());
        assertNotNull("Some Book Title", lexRequest.getSlots().get(IntentSlotName.BookTitle));
        assertNotNull("Some Author", lexRequest.getSlots().get(IntentSlotName.BookAuthor));
        assertFalse(lexRequest.getSessionAttributes().isEmpty());
        assertNotNull("SessionVal1", lexRequest.getSessionAttributes().get("attr1"));
        assertNotNull("SessionVal2", lexRequest.getSessionAttributes().get("attr2"));
    }
}