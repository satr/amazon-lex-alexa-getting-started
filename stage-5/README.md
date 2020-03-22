## Stage 5
### Add Amazon Alexa Skill

#### Create AWS Lambda function:
 * Name: `BookStoreAskLambdaJavaStage5`
 * Runtime: `Java 11`
 * Handler: `io.github.satr.aws.lambda.bookstore.BookStoreAskLambda::handleRequest`

NB: "ASK" stans for "Alexa Skill Kit".

In the Lambda - add a trigger `Alexa Skills Kit`.

Command to upload function-code (when current dir is root-folder of the repo)
```
aws lambda update-function-code --function-name BookStoreAskLambdaJavaStage5 --zip-file fileb://stage-5/lambda/bookstore-aws-lambda-java/build/libs/bookstore-aws-lambda-java-1.0-Stage-5.jar
```

New dependency in the file `build.gradle`
```
dependencies {
    //...
    compile group: 'com.amazon.alexa', name: 'ask-sdk', version:'2.27.2'
    compile group: 'com.amazonaws', name: 'aws-lambda-java-log4j2', version:'1.1.0'
}
```

Add a file log4j configuration file `log4j2.xml` to the `src/resources` with a content (log message pattern can be customised):
```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration packages="com.amazonaws.services.lambda.runtime.log4j2">
    <Appenders>
        <Lambda name="Lambda">
            <PatternLayout>
                <pattern>%d{yyyy-MM-dd HH:mm:ss} %X{AWSRequestId} %-5p %c{1}:%L - %m%n</pattern>
            </PatternLayout>
        </Lambda>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="Lambda" />
        </Root>
    </Loggers>
</Configuration>
```

If Alexa Skill test simulator does not respond - try to add temporarily following URLs as exceptions to your antivirus (e.g. to Web-Shield of Avast - remove those exceptions abter end of testing):
```
https://developer.amazon.com/alexa
https://avs-alexa-na.amazon.com
```
 Or (not recommended) - temporarily disable antivirus til a first respond and enable it again then.