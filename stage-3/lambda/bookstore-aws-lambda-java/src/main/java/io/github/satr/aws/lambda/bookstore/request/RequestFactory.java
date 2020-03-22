package io.github.satr.aws.lambda.bookstore.request;
// Copyright © 2020, github.com/satr, MIT License

import java.util.Map;

public final class RequestFactory {
    public static Request createFrom(Map<String, Object> input) {
        Request request = new Request();
        if(input == null)
            return request;

        loadMainAttributes(input, request);
        loadBotName(input, request);
        loadSessionAttributes(input, request);

        Map<String, Object> currentIntent = loadCurrentIntent(input);
        if (currentIntent != null)
            loadIntentParameters(currentIntent, request);

        return request;
    }

    private static void loadMainAttributes(Map<String, Object> input, Request request) {
        loadUserId(input, request);
        request.setInputTranscript((String) input.get(LexRequestAttribute.InputTranscript));
        request.setInvocationSource(getInvocationSource(input));
        request.setOutputDialogMode(getOutputDialogMode(input));
    }

    private static void loadUserId(Map<String, Object> input, Request request) {
        String userId = (String) input.get(LexRequestAttribute.UserId);
        request.setUserId(userId);
    }

    private static OutputDialogMode getOutputDialogMode(Map<String, Object> input) {
        return LexRequestAttribute.OutputDialogModeValue.Voice.equals(input.get(LexRequestAttribute.OutputDialogMode))
                ? OutputDialogMode.Voice : OutputDialogMode.Text;
    }

    private static InvocationSource getInvocationSource(Map<String, Object> input) {
        return LexRequestAttribute.InvocationSourceValue.DialogCodeHook.equals(input.get(LexRequestAttribute.InvocationSource))
                ? InvocationSource.DialogCodeHook : InvocationSource.FulfillmentCodeHook;
    }

    private static void loadSessionAttributes(Map<String, Object> input, Request request) {
        Map<String, Object> sessionAttrs = (Map<String, Object>) input.get(LexRequestAttribute.SessionAttributes);
        if (sessionAttrs != null)
            request.setSessionAttributes(sessionAttrs);
    }

    private static void loadIntentParameters(Map<String, Object> currentIntent, Request request) {
        request.setConfirmationStatus(getConfirmationStatus(currentIntent));
        request.setIntentName((String) currentIntent.get(LexRequestAttribute.CurrentIntentName));

        loadIntentSlots(currentIntent, request);
    }

    private static ConfirmationStatus getConfirmationStatus(Map<String, Object> currentIntent) {
        String confirmationStatus = (String) currentIntent.get(LexRequestAttribute.InvocationSource);
        return LexRequestAttribute.ConfirmationStatusValue.Confirmed.equals(confirmationStatus)
                ? ConfirmationStatus.Confirmed
                : LexRequestAttribute.ConfirmationStatusValue.Denied.equals(confirmationStatus)
                ? ConfirmationStatus.Denied
                : ConfirmationStatus.None;
    }

    private static Map<String, Object> loadCurrentIntent(Map<String, Object> input) {
        return (Map<String, Object>) input.get(LexRequestAttribute.CurrentIntent);
    }

    private static void loadBotName(Map<String, Object> input, Request request) {
        Map<String, Object> bot = (Map<String, Object>) input.get(LexRequestAttribute.Bot);
        if (bot != null)
            request.setBotName((String) bot.get(LexRequestAttribute.BotName));
    }

    private static void loadIntentSlots(Map<String, Object> currentIntent, Request request) {
        Map<String, Object> slots = (Map<String, Object>) currentIntent.get(LexRequestAttribute.Slots);
        request.setIntentSlots(slots);
    }
}
