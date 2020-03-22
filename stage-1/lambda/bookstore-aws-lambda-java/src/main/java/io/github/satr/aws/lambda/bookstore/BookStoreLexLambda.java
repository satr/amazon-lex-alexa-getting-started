package io.github.satr.aws.lambda.bookstore;
// Copyright © 2020, github.com/satr, MIT License

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.request.RequestFactory;
import io.github.satr.aws.lambda.bookstore.response.DialogAction;
import io.github.satr.aws.lambda.bookstore.response.Response;
import io.github.satr.aws.lambda.bookstore.response.Message;

import java.util.Map;

//Lambda with POJO as a response
public class BookStoreLexLambda implements RequestHandler<Map<String, Object>, Object>  {
    @Override
    public Object handleRequest(Map<String, Object> input, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log(input.toString());//Just to show the input in the CloudWatch log

        //Read input
        Request request = RequestFactory.createFrom(input);

        //Log input properties
        logInputProperties(logger, request);

        //Perform some action
        String respondMessage = request.getIntentName().equals("OrderBookIntent")
                ? String.format("Ordered a book \"%s\" by \"%s\"", request.getSlots().get("BookName"), request.getSlots().get("AuthorLastName"))
                : String.format("Received Intent: %s", request.getIntentName());

        //Prepare respond
        Message message = new Message(Message.ContentType.PlainText, respondMessage);
        DialogAction dialogAction = new DialogAction(DialogAction.Type.Close, DialogAction.FulfillmentState.Fulfilled, message);
        Response response = new Response(dialogAction);
        return response;
    }

    private void logInputProperties(LambdaLogger logger, Request request) {
        logger.log("UserId:" + request.getUserId());
        logger.log("Bot name:" + request.getBotName());
        logger.log("Current intent name:" + request.getIntentName());

        Map<String, Object> slots = request.getSlots();
        logger.log(slots.keySet().isEmpty() ? "No Slots" : "Slots:");
        for (String slotName : slots.keySet())
            logger.log(" - " + slotName + ":" + slots.get(slotName));

        Map<String, Object> sessionAttributes = request.getSessionAttributes();
        logger.log(sessionAttributes.keySet().isEmpty() ? "No Session Attributes" : "Session Attributes:");
        for (String attr : sessionAttributes.keySet())
            logger.log(" - " + attr + ":" + sessionAttributes.get(attr));
    }
}
