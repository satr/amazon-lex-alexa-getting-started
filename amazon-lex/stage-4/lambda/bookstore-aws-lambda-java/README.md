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
### Create database tables
In the AWS DynamoDb database - create two tables: `Basket` and `BookSearchResult`, with with "Primary partition key": isbn, with type "String"
### Add to AWS Lambda role policy to access AWS DynamoDb
```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "VisualEditor0",
            "Effect": "Allow",
            "Action": [
                "dynamodb:BatchGetItem",
                "dynamodb:BatchWriteItem",
                "dynamodb:PutItem",
                "dynamodb:DeleteItem",
                "dynamodb:GetItem",
                "dynamodb:Scan",
                "dynamodb:Query",
                "dynamodb:UpdateItem"
            ],
            "Resource": [
                "arn:aws:dynamodb:*:YOUR_ACCOUNT_NUMBER:table/Basket",
                "arn:aws:dynamodb:*:YOUR_ACCOUNT_NUMBER:table/BookSearchResult"
            ]
        },
        {
            "Sid": "VisualEditor1",
            "Effect": "Allow",
            "Action": [
                "dynamodb:ListTables",
                "dynamodb:ListStreams"
            ],
            "Resource": "*"
        }
    ]
}
``` 