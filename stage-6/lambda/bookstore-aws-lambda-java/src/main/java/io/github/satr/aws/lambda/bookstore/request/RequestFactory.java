package io.github.satr.aws.lambda.bookstore.request;
// Copyright © 2022, github.com/satr, MIT License

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Slot;
import com.amazon.ask.model.slu.entityresolution.Resolution;
import com.amazon.ask.model.slu.entityresolution.Resolutions;
import com.amazon.ask.model.slu.entityresolution.ValueWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RequestFactory {
    public static Request createFromLexInput(Map<String, Object> input) {
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

    // https://developer.amazon.com/en-US/docs/alexa/custom-skills/request-types-reference.html
    public static Request createFromAskInput(HandlerInput handlerInput) {
        RequestEnvelope requestEnvelope = handlerInput.getRequestEnvelope();
        Request request = new Request();
        request.setSessionId(requestEnvelope.getSession().getSessionId());
        request.setSessionAttributes(handlerInput.getAttributesManager().getSessionAttributes());
        request.setUserId(requestEnvelope.getSession().getUser().getUserId());
        request.setIntentSlots(getSlotsFromAsk(requestEnvelope));
        return request;
    }

    private static Map<String, Object> getSlotsFromAsk(RequestEnvelope envelope) {
        Map<String, Slot> slots = ((IntentRequest) envelope.getRequest()).getIntent().getSlots();
        Map<String, Object> slotMap = new HashMap<>();
        for (Slot item : slots.values()) {
            slotMap.put(item.getName(), getFirstResolvedSlotValue(item));
        }
        return slotMap;
    }

    // https://developer.amazon.com/en-US/docs/alexa/custom-skills/define-synonyms-and-ids-for-slot-type-values-entity-resolution.html#intentrequest-changes
    private static String getFirstResolvedSlotValue(Slot item) {
        Resolutions resolutions = item.getResolutions();
        if(resolutions == null)
            return item.getValue();
        List<Resolution> resolutionsPerAuthority = resolutions.getResolutionsPerAuthority();
        if(resolutionsPerAuthority.isEmpty())
            return null;
        List<ValueWrapper> resolvedValues = resolutionsPerAuthority.get(0).getValues();
        return resolvedValues.isEmpty() ? null : resolvedValues.get(0).getValue().getName();
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
