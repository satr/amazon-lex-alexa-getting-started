## Stage 5
### Add Amazon Alexa Skill

#### Create AWS Lambda function:
 * Name: `BookStoreAlexaSkillLambdaJavaStage1`
 * Runtime: `Java 11`
 * Handler: `io.github.satr.aws.lambda.bookstore.BookStoreAlexaSkillLambda::handleRequest`

In the Lambda - add a trigger `Alexa Skills Kit`

Command to upload function-code (when current dir is root-folder of the repo)
```
aws lambda update-function-code --function-name BookStoreAlexaSkillLambdaJavaStage1 --zip-file fileb://stage-5/lambda/bookstore-aws-lambda-java/build/libs/bookstore-aws-lambda-java-1.0-Stage-5.jar
```

New dependency in the file `build.gradle`
```
dependencies {
    //...
    compile group: 'com.amazon.alexa', name: 'ask-sdk', version:'2.27.2'
    compile group: 'com.amazonaws', name: 'aws-lambda-java-log4j2', version:'1.1.0'
}
```
