package io.github.satr.aws.lambda.bookstore.respond;
// Copyright © 2020, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.request.LexRequest;

import java.util.Map;

public class LexRespond {
    private DialogAction dialogAction;
    private Map<String, Object> sessionAttributes;
//    @JsonIgnore
//    private String sessionId;

    public LexRespond(DialogAction dialogAction) {

        this.dialogAction = dialogAction;
    }

    public DialogAction getDialogAction() {
        return dialogAction;
    }

    public void setSessionAttributes(Map<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

//    @JsonIgnore
//    public void setSessionId(String sessionId) {
//        this.sessionId = sessionId;
//    }

//    @JsonIgnore
//    public String getSessionId() {
//        return sessionId;
//    }

    public void setSessionFrom(LexRequest request) {
        setSessionAttributes(request.getSessionAttributes());
//        setSessionId(request.getSessionIdOrCreateIfEmpty());
    }

    public void setSessionAttribute(String key, Object value) {
        getSessionAttributes().put(key, value);
    }
}
