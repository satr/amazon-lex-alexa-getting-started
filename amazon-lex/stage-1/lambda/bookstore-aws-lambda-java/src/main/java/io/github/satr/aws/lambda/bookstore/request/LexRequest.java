package io.github.satr.aws.lambda.bookstore.request;

public class LexRequest {
    private String botName;
    private String intentName;
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
}
