package io.github.satr.aws.lambda.bookstore.request;
// Copyright © 2022, github.com/satr, MIT License

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Request {
    private String botName;
    private String intentName;
    private String userId;
    private Map<String, Object> sessionAttributes = new HashMap<>();
    private ConfirmationStatus confirmationStatus = ConfirmationStatus.None;
    private Map<String, Object> intentSlots = new HashMap<>();
    private InvocationSource invocationSource = InvocationSource.FulfillmentCodeHook;
    private String inputTranscript;
    private OutputDialogMode outputDialogMode = OutputDialogMode.Text;
    private String sessionId;

    public void setBotName(String botName) {
        this.botName = botName;
    }
    public String getBotName() {
        return botName;
    }
    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }
    public String getIntentName() {
        return intentName;
    }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserId() {
        return userId;
    }

    public void setSessionAttributes(Map<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public String getSessionAttribute(String key) {
        return (String) sessionAttributes.get(key);
    }

    public void setConfirmationStatus(ConfirmationStatus confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    public ConfirmationStatus getConfirmationStatus() {
        return confirmationStatus;
    }

    public void setIntentSlots(Map<String, Object> intentSlots) {
        this.intentSlots = intentSlots;
    }

    public Map<String, Object> getSlots() {
        return intentSlots;
    }

    public void setInvocationSource(InvocationSource invocationSource) {
        this.invocationSource = invocationSource;
    }

    public InvocationSource getInvocationSource() {
        return invocationSource;
    }

    public void setInputTranscript(String inputTranscript) {
        this.inputTranscript = inputTranscript;
    }

    public String getInputTranscript() {
        return inputTranscript;
    }

    public void setOutputDialogMode(OutputDialogMode outputDialogMode) {
        this.outputDialogMode = outputDialogMode;
    }

    public OutputDialogMode getOutputDialogMode() {
        return outputDialogMode;
    }

    public String getSlot(String slotName) {
        return (String) getSlots().get(slotName);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionIdOrCreateIfEmpty() {
        return sessionId != null ? sessionId : UUID.randomUUID().toString();
    }
}
