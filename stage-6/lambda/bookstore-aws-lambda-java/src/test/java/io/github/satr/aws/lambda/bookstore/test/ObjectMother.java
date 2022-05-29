package io.github.satr.aws.lambda.bookstore.test;
// Copyright © 2022, github.com/satr, MIT License

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.amazonaws.util.StringInputStream;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.repositories.database.tableentity.BasketItem;
import io.github.satr.aws.lambda.bookstore.repositories.database.tableentity.BookSearchResultItem;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.request.RequestFactory;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class ObjectMother {
    private static ObjectMapper jsonObjectMapper;
    private static Random random = new Random();

    public static Map<String, Object> createMapFromJson(String jsonFileName){
        File jsonFile = getFileFormJsonFile(jsonFileName);
        if (jsonFile == null)
            return new HashMap<>();
        jsonObjectMapper = new ObjectMapper();
        try {
            Map<String, Object> dataMap = jsonObjectMapper.readValue(jsonFile, new TypeReference<Map<String, Object>>() {
            });
            return dataMap;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static StringInputStream createInputStreamFromJson(String jsonFileName){
        File jsonFile = getFileFormJsonFile(jsonFileName);
        if (jsonFile == null)
            return null;
        try {
            return new StringInputStream(FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static File getFileFormJsonFile(String jsonFileName) {
        ClassLoader classLoader = ObjectMother.class.getClassLoader();
        File jsonFile = new File(classLoader.getResource(jsonFileName).getFile());
        if(!jsonFile.exists()) {
            System.out.println("File not found: " + jsonFileName);
            return null;
        }
        return jsonFile;
    }

    public static Request createLexRequestForOrderBook(String bookTitle, String bookAuthor) {
        Request request = new Request();
        request.getSlots().put(IntentSlotName.BookTitle, bookTitle);
        request.getSlots().put(IntentSlotName.BookAuthor, bookAuthor);
        return request;
    }

    public static Request createLexRequestForSelectBook(String chooseFromListAction, String itemNumber, String positionInSequence) {
        Request request = new Request();
        request.getSlots().put(IntentSlotName.ItemNumber, itemNumber);
        request.getSlots().put(IntentSlotName.PositionInSequence, positionInSequence);
        request.getSlots().put(IntentSlotName.ChooseFromListAction, chooseFromListAction);
        return request;
    }

    public static Request createLexRequestFromJson(String jsonFileName) {
        return RequestFactory.createFromLexInput(ObjectMother.createMapFromJson(jsonFileName));
    }

    public static String getRandomString() {
        return UUID.randomUUID().toString();
    }

    public static List<Book> getRandomBookList(int amount) {
        LinkedList<Book> books = new LinkedList<>();
        for (int i = 0; i < amount; i++)
            books.add(getRandomBook());
        return books;
    }

    public static Book getRandomBook() {
        return getRandomlyPopulatedBook(new Book());
    }

    public static List<BasketItem> getRandomBasketItemList(int amount) {
        LinkedList<BasketItem> books = new LinkedList<>();
        for (int i = 0; i < amount; i++)
            books.add(getRandomBasketItem());
        return books;
    }

    public static List<BookSearchResultItem> getRandomBookSearchResultItemList(int amount) {
        LinkedList<BookSearchResultItem> books = new LinkedList<>();
        for (int i = 0; i < amount; i++)
            books.add(getRandomBookSearchResultItem());
        return books;
    }

    public static BasketItem getRandomBasketItem() {
        return (BasketItem) getRandomlyPopulatedBook(new BasketItem());
    }

    public static BookSearchResultItem getRandomBookSearchResultItem() {
        return (BookSearchResultItem) getRandomlyPopulatedBook(new BookSearchResultItem());
    }


    private static Book getRandomlyPopulatedBook(Book book) {
        book.setTitle("Title-" + getRandomString());
        book.setAuthor("Author-" + getRandomString());
        book.setIssueYear(getRandomInt(1900, 2020));
        book.setPrice(getRandomFloat(1.5f, 50.9f));
        return book;
    }

    public static int getRandomInt(int min, int max) {
        return min + random.nextInt(max - min);
    }

    public static float getRandomFloat(float min, float max) {
        int minFloat = (int)(min * 100);
        int maxFloat = (int)(max * 100);
        return ((float)(minFloat + random.nextInt(maxFloat - minFloat)))/100;
    }

    public static AmazonDynamoDB createInMemoryDb() {
        AmazonDynamoDB dynamodb = null;
        try {
            // Create an in-memory and in-process instance of DynamoDB Local
            AmazonDynamoDBLocal amazonDynamoDBLocal = DynamoDBEmbedded.create();
            dynamodb = amazonDynamoDBLocal.amazonDynamoDB();
            return dynamodb;
        } catch (Exception e){
            if(dynamodb != null)
                dynamodb.shutdown();// Shutdown the thread pools in DynamoDB Local / Embedded
        }
        return dynamodb;
    }
}
