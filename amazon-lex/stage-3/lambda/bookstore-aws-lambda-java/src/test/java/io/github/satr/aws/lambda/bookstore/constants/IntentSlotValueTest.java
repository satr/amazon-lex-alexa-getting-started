package io.github.satr.aws.lambda.bookstore.constants;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IntentSlotValueTest {
    @Test
    public void test() {
        Integer number = IntentSlotValue.PositionInSequence.getNumberInSequenceByPosition(IntentSlotValue.PositionInSequence.First, -1);
        assertNotNull(number);
        assertEquals(1, (int)number);
    }
}