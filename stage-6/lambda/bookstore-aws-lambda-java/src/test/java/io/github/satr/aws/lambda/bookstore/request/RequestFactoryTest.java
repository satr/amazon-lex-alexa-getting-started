package io.github.satr.aws.lambda.bookstore.request;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import io.github.satr.aws.lambda.bookstore.test.ObjectMother;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class RequestFactoryTest {

    @Test
    public void createFromLexInput() {
        Map<String, Object> input = ObjectMother.createMapFromJson("simple-search-book-by-title-intent-request-lex.json");

        Request request = RequestFactory.createFromLexInput(input);

        assertNotNull(request);
        assertNotNull(request.getSessionAttributes());
        assertNotNull(request.getSlots());
        assertEquals("BotName", request.getBotName());
        assertEquals(InvocationSource.DialogCodeHook, request.getInvocationSource());
        assertEquals("user-id-as-some-random-character-set", request.getUserId());
        assertEquals("SearchBookByTitleIntent", request.getIntentName());
        assertFalse(request.getSlots().isEmpty());
        assertNotNull("Some Book Title", request.getSlots().get(IntentSlotName.BookTitle));
        assertNotNull("starts", request.getSlots().get(IntentSlotName.WordsPosition));
        assertFalse(request.getSessionAttributes().isEmpty());
        assertNotNull("SessionVal1", request.getSessionAttributes().get("attr1"));
        assertNotNull("SessionVal2", request.getSessionAttributes().get("attr2"));
    }
}