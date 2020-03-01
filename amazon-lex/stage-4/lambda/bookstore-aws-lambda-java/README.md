## Stage 4
### Add storage in DynamoDB

#### Create AWS Lambda function:
 * Name: `BookStoreLambdaJavaStage4`
 * Runtime: `Java 11`
 * Handler: `io.github.satr.aws.lambda.bookstore.BookStoreLambda::handleRequest`

New dependency in the file `build.gradle`
```
dependencies {
    //...
    compile "com.amazonaws:aws-java-sdk-dynamodb:1.11.728"
}
```

Command to upload function-code (when current dir is root-folder of the repo)
```
aws lambda update-function-code --function-name BookStoreLambdaJavaStage4 --zip-file fileb://amazon-lex/stage-4/lambda/bookstore-aws-lambda-java/build/libs/bookstore-aws-lambda-java-1.0-Stage-4.jar
```