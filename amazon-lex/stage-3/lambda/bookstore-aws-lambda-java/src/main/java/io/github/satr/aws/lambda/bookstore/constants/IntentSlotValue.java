package io.github.satr.aws.lambda.bookstore.constants;
// Copyright © 2020, github.com/satr, MIT License

import java.util.HashMap;
import java.util.Map;

public final class IntentSlotValue {
    public static class WordsPosition {
        public static String Starts = "starts";
        public static String Ends = "ends";
        public static String Contains = "contains";
    }

    public static class ChooseFromListAction {
        public static String Show = "show";
        public static String Order = "order";
        public static String Remove = "remove";
    }

    public static class PositionInSequence {
        private static Map<String, Integer> positionInSequenceToNumber;

        public static String First = "first";
        public static String Second = "second";
        public static String Third = "third";
        public static String Fourth = "fourth";
        public static String Fifth = "fifth";
        public static String Sixth = "sixth";
        public static String Seventh = "seventh";
        public static String Eighth = "eighth";
        public static String Ninth = "ninth";
        public static String Tenth = "tenth";
        public static String Eleventh = "Eleventh";
        public static String Twelfth = "twelfth";
        public static String Last = "last";

        public static Integer getNumberInSequenceByPosition(String positionInSequence, int lastValue) {
            if (positionInSequence == null)
                return null;
            return Integer.valueOf(positionInSequence.equals(Last) ? lastValue : getPositionInSequenceToNumber().get(positionInSequence));
        }

        private static synchronized Map<String, Integer> getPositionInSequenceToNumber() {
            if (positionInSequenceToNumber != null)
                return positionInSequenceToNumber;

            return positionInSequenceToNumber = new HashMap<String, Integer>(){{
                put(IntentSlotValue.PositionInSequence.First, 1);
                put(IntentSlotValue.PositionInSequence.Second, 2);
                put(IntentSlotValue.PositionInSequence.Third, 3);
                put(IntentSlotValue.PositionInSequence.Fourth,4);
                put(IntentSlotValue.PositionInSequence.Fifth, 5);
                put(IntentSlotValue.PositionInSequence.Sixth, 6);
                put(IntentSlotValue.PositionInSequence.Seventh, 7);
                put(IntentSlotValue.PositionInSequence.Eighth, 8);
                put(IntentSlotValue.PositionInSequence.Ninth, 9);
                put(IntentSlotValue.PositionInSequence.Tenth, 10);
                put(IntentSlotValue.PositionInSequence.Eleventh, 11);
                put(IntentSlotValue.PositionInSequence.Twelfth, 12);
            }};
        }
    }
}
