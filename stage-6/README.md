## Stage 6
### Adopt Amazon Lambda output for Alexa voice output 

#### Create AWS Lambda function:
 * Name: `BookStoreAskLambdaJavaStage6`
 * Runtime: `Java 11`
 * Handler: `io.github.satr.aws.lambda.bookstore.BookStoreAskLambda::handleRequest`
 Copy lambda's ARN.

NB: "ASK" stands for "Alexa Skill Kit".

In [Alexa console](https://developer.amazon.com/alexa/console) create an Amazon Alexa Skill with a name BookStoreTwo, invocation name `bookstore two` and put to the intent JSON editor content of the file `stage-6/alexa-skill/BookStoreTwoAskStage6.json`. In the `Endpoint` section - put to the `Default Region` fierd lambda's ARN. Copy skill's ARN from `Your Skill ID`.

In the Lambda settings - add a trigger `Alexa Skills Kit`. Keep switch `Skill ID verification` on `Enabled`, put copied skill's ARN to the `SKILL ID` field and hit `Add` button. Save the lambda function. Test it (button `Test`) with template `AlexaSkillStart`.

In the lambda's `Permissions` section (or tab) click on the role link. On the Role summary page hit the `Attach policies` button. In the `Add permissions` page find (filter) policy `AmazonDynamoDBFullAccess` - select it and hit the `Attach policy` button.

Command to upload function-code (when current dir is root-folder of the repo)
```
aws lambda update-function-code --function-name BookStoreAskLambdaJavaStage6 --zip-file fileb://stage-6/lambda/bookstore-aws-lambda-java/build/libs/bookstore-aws-lambda-java-1.0-Stage-6.jar
```

In the Alexa dashboard - switch to the `Test` section (or tab). Switch to `Development` dropdown `Skill testing is enabled in`. Test skill invoking `bookstore two`. E.g. `ask bookstore two hello` or `do you have a book which title contains friday`.
