package io.github.satr.aws.lambda.bookstore.constants;
// Copyright © 2022, github.com/satr, MIT License

import org.junit.Test;

import static org.junit.Assert.*;

public class IntentSlotValueTest {
    @Test
    public void getFirst() {
        Integer number = IntentSlotValue.PositionInSequence.getNumberInSequenceByPosition(IntentSlotValue.PositionInSequence.First, -1);
        assertNotNull(number);
        assertEquals(1, (int)number);
    }

    @Test
    public void getSecond() {
        Integer number = IntentSlotValue.PositionInSequence.getNumberInSequenceByPosition(IntentSlotValue.PositionInSequence.Second, -1);
        assertNotNull(number);
        assertEquals(2, (int)number);
    }

    @Test
    public void getThird() {
        Integer number = IntentSlotValue.PositionInSequence.getNumberInSequenceByPosition(IntentSlotValue.PositionInSequence.Third, -1);
        assertNotNull(number);
        assertEquals(3, (int)number);
    }

    @Test
    public void getLast() {
        Integer number = IntentSlotValue.PositionInSequence.getNumberInSequenceByPosition(IntentSlotValue.PositionInSequence.Last, 77);
        assertNotNull(number);
        assertEquals(77, (int)number);
    }

    @Test
    public void getWithNull() {
        Integer number = IntentSlotValue.PositionInSequence.getNumberInSequenceByPosition(null, 77);
        assertNull(number);
    }
}