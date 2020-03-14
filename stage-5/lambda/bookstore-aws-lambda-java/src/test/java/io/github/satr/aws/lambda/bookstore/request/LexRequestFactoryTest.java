package io.github.satr.aws.lambda.bookstore.request;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class LexRequestFactoryTest {

    @Test
    public void createFromOrderBookIntent() {
        Map<String, Object> input = ObjectMother.createMapFromJson("simple-search-book-by-title-intent-request.json");

        LexRequest lexRequest = LexRequestFactory.createFrom(input);

        assertNotNull(lexRequest);
        assertNotNull(lexRequest.getSessionAttributes());
        assertNotNull(lexRequest.getSlots());
        assertEquals("BotName", lexRequest.getBotName());
        assertEquals(InvocationSource.DialogCodeHook, lexRequest.getInvocationSource());
        assertEquals("user-id-as-some-random-character-set", lexRequest.getUserId());
        assertEquals("SearchBookByTitleIntent", lexRequest.getIntentName());
        assertFalse(lexRequest.getSlots().isEmpty());
        assertNotNull("Some Book Title", lexRequest.getSlots().get(IntentSlotName.BookTitle));
        assertNotNull("starts", lexRequest.getSlots().get(IntentSlotName.WordsPosition));
        assertFalse(lexRequest.getSessionAttributes().isEmpty());
        assertNotNull("SessionVal1", lexRequest.getSessionAttributes().get("attr1"));
        assertNotNull("SessionVal2", lexRequest.getSessionAttributes().get("attr2"));
    }
}