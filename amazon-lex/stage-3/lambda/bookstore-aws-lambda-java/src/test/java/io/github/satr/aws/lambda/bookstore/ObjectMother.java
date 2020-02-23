package io.github.satr.aws.lambda.bookstore;
// Copyright © 2020, github.com/satr, MIT License

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.satr.aws.lambda.bookstore.constants.IntentSlotName;
import io.github.satr.aws.lambda.bookstore.entity.Book;
import io.github.satr.aws.lambda.bookstore.request.LexRequest;
import io.github.satr.aws.lambda.bookstore.request.LexRequestFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class ObjectMother {
    private static ObjectMapper jsonObjectMapper;

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

    public static LexRequest createLexRequestWithBookBookTitleAndAuthor(String bookTitle, String bookAuthor) {
        LexRequest request = new LexRequest();
        request.getSlots().put(IntentSlotName.BookTitle, bookTitle);
        request.getSlots().put(IntentSlotName.BookAuthor, bookAuthor);
        return request;
    }

    public static LexRequest createLexRequestFromJson(String jsonFileName) {
        return LexRequestFactory.createFrom(ObjectMother.createMapFromJson(jsonFileName));
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

    private static Book getRandomBook() {
        Book book = new Book();
        book.setTitle(getRandomString());
        book.setAuthor(getRandomString());
        return book;
    }

    public static ObjectNode getJsonObjectNode() {
        return jsonObjectMapper.createObjectNode();
    }
}
