## Stage 1
## AWS Lambda fuction for basic Amazon Lex requests handling

#### Create AWS Lambda function:
 * Name: `BookStoreLexLambdaJavaStage1`
 * Runtime: `Java 11`
 * Handler: `io.github.satr.aws.lambda.bookstore.BookStoreLexLambda::handleRequest`

### Request
_it can be used as an input for a test_
```json
{
  "messageVersion": "1.0",
  "invocationSource": "DialogCodeHook",
  "userId": "ucflekvs05b5qa2hdi56573mv2dn7sgt",
  "sessionAttributes": {},
  "bot": {
    "name": "BotName",
    "alias": "$LATEST",
    "version": "$LATEST"
  },
  "outputDialogMode": "Text",
  "currentIntent": {
    "name": "RecognisedIntentName",
    "slots": {},
    "confirmationStatus": "None"
  }
}
```
where:
```
"invocationSource": "DialogCodeHook" or "FulfillmentCodeHook"
"outputDialogMode": "Text" or "Voice"
"confirmationStatus": "None", "Confirmed" or "Denied"
```

### Response (minimum format)
```json
{
    "dialogAction": {
        "type": "Close",
        "fulfillmentState": "Fulfilled",
        "message": {
            "contentType": "PlainText",
            "content": "Custom response message"
        }
    }
}
```
### Response with a `Map<String, Object>`
```java
public class BookStoreLexLambda implements RequestHandler<Map<String, Object>, Object>  {
    @Override
    public Object handleRequest(Map<String, Object> input, Context context) {
        String respondMessage = "Custom response message";

        Map<String, Object> response = new HashMap<>();

        Map<String, Object> dialogAction = new HashMap<>();
        dialogAction.put("type", "Close");
        dialogAction.put("fulfillmentState", "Fulfilled");
        response.put("dialogAction", dialogAction);

        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("contentType", "PlainText");
        messageMap.put("content", respondMessage);
        dialogAction.put("message", messageMap);
        return response;
    }
}
```
### Response with a `json`
```java
public class BookStoreLexLambda implements RequestHandler<Map<String, Object>, Object>  {
    @Override
    public Object handleRequest(Map<String, Object> input, Context context) {
        String respondMessage = "Custom response message";

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
```
### Response with a `POJO`
```java
public class BookStoreLexLambda implements RequestHandler<Map<String, Object>, Object>  {
    @Override
    public Object handleRequest(Map<String, Object> input, Context context) {
        String respondMessage = "Custom respond message";
        Message message = new Message(Message.ContentType.PlainText, respondMessage);
        DialogAction dialogAction = new DialogAction(DialogAction.Type.Close, DialogAction.FulfillmentState.Fulfilled, message);
        Response response = new Response(dialogAction);
        return respond;
    }
}
```
