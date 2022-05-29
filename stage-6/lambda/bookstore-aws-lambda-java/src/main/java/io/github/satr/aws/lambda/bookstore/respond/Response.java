package io.github.satr.aws.lambda.bookstore.respond;
// Copyright © 2022, github.com/satr, MIT License

import io.github.satr.aws.lambda.bookstore.request.Request;

import java.util.Map;

public class Response {
    private DialogAction dialogAction;
    private Map<String, Object> sessionAttributes;
//    @JsonIgnore
//    private String sessionId;

    public Response(DialogAction dialogAction) {

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

    public void setSessionFrom(Request request) {
        setSessionAttributes(request.getSessionAttributes());
//        setSessionId(request.getSessionIdOrCreateIfEmpty());
    }

    public void setSessionAttribute(String key, Object value) {
        getSessionAttributes().put(key, value);
    }

    public void removeSessionAttribute(String attributeKey) {
        getSessionAttributes().remove(attributeKey);
    }
}
