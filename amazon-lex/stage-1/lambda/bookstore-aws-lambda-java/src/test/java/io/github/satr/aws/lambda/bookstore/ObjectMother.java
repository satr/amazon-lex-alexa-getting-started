package io.github.satr.aws.lambda.bookstore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ObjectMother {
    public static Map<String, Object> createMapFromJson(String jsonFilePath){
        ClassLoader classLoader = ObjectMother.class.getClassLoader();
        File jsonFile = new File(classLoader.getResource(jsonFilePath).getFile());
        if(!jsonFile.exists()) {
            System.out.println("File not found: " + jsonFilePath);
            return new HashMap<>();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> dataMap = objectMapper.readValue(jsonFile, new TypeReference<Map<String, Object>>() {
            });
            return dataMap;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
