## Stage 2
### Separate logic, handling requests for different intents

#### Create AWS Lambda function:
 * Name: `BookStoreLexLambdaJavaStage2`
 * Runtime: `Java 11`
 * Handler: `io.github.satr.aws.lambda.bookstore.BookStoreLexLambda::handleRequest`

This project (and further stages) use `POJO` responses (Lambda functions with `json` and `Map<>` from stage-1 work similar way).

Upload `jar`-file via AWS-console Lambda [web-interface](https://console.aws.amazon.com/lambda/home?region=us-east-1#/functions) 

or with `aws-cli`
```sh
aws lambda update-function-code --function-name BookStoreLexLambdaJavaStage2 --zip-file fileb://PATH-TO-PROJECT/build/lib/bookstore-aws-lambda-java-1.0-Stage-2.jar
```
Where:
 * `BookStoreLexLambdaJavaStage2` is a AWS Lambda function name
 * `PATH-TO-PROJECT` - path to this project
 * `bookstore-aws-lambda-java-1.0-Stage-2.jar` - built `jar`-file
