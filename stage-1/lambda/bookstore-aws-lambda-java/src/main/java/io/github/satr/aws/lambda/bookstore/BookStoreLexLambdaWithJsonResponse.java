package io.github.satr.aws.lambda.bookstore;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.Map;

//Lambda with JSON as a respond
public class BookStoreLexLambdaWithJsonResponse implements RequestHandler<Map<String, Object>, Object>  {
    @Override
    public Object handleRequest(Map<String, Object> input, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log(input.toString());//Just to show the input in the CloudWatch log

        //Read input
        String userId = (String) input.get("userId");
        Map<String, Object> bot = (Map<String, Object>) input.get("bot");
        Object botName = bot.get("name");
        Map<String, Object> currentIntent = (Map<String, Object>) input.get("currentIntent");
        String intentName = (String) currentIntent.get("name");
        Map<String, Object> slots = (Map<String, Object>) currentIntent.get("slots");
        Map<String, Object> sessionAttributes = (Map<String, Object>) input.get("sessionAttributes");

        //Log input properties
        logger.log("UserId:" + userId);
        logger.log("Bot name:" + botName);
        logger.log("Current intent name:" + intentName);

        logger.log(slots.keySet().isEmpty() ? "No Slots" : "Slots:");
        for (String slotName : slots.keySet())
            logger.log(" - " + slotName + ":" + slots.get(slotName));

        logger.log(sessionAttributes.keySet().isEmpty() ? "No Session Attributes" : "Session Attributes:");
        for (String attr : sessionAttributes.keySet())
            logger.log(" - " + attr + ":" + sessionAttributes.get(attr));

        //Perform some action
        String respondMessage = intentName.equals("OrderBookIntent")
                ? String.format("Ordered a book \"%s\" by \"%s\"", slots.get("BookName"), slots.get("AuthorLastName"))
                : String.format("Received Intent: %s", intentName);

        //Prepare respond
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        ObjectNode dialogActionNode = mapper.createObjectNode();
        dialogActionNode.put("type", "Close");
        dialogActionNode.put("fulfillmentState", "Fulfilled");
        responseNode.set("dialogAction", dialogActionNode);

        ObjectNode messageNode = mapper.createObjectNode();
        messageNode.put("contentType", "PlainText");
        messageNode.put("content", respondMessage);
        dialogActionNode.set("message", messageNode);
        try {
            return mapper.readValue(responseNode.toString(), new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}