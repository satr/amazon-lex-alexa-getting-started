package io.github.satr.aws.lambda.bookstore.test;
// Copyright © 2020, github.com/satr, MIT License

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.request.Request;
import io.github.satr.aws.lambda.bookstore.request.RequestFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class ObjectMother {
    private static ObjectMapper jsonObjectMapper;
    private static Random random = new Random();

    public static Map<String, Object> createMapFromJson(String jsonFileName){
        ClassLoader classLoader = ObjectMother.class.getClassLoader();
        File jsonFile = new File(classLoader.getResource(jsonFileName).getFile());
        if(!jsonFile.exists()) {
            System.out.println("File not found: " + jsonFileName);
            return new HashMap<>();
        }
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
        return RequestFactory.createFrom(ObjectMother.createMapFromJson(jsonFileName));
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
        Book book = new Book();
        book.setTitle(getRandomString());
        book.setAuthor(getRandomString());
        book.setIssueYear(getRandomInt(1900, 2020));
        return book;
    }

    private static int getRandomInt(int min, int max) {
        return min + random.nextInt(max - min);
    }
}
