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