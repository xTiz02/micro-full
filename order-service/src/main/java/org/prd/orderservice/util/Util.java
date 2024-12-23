package org.prd.orderservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class Util {
    public static boolean isValidField(String field){
        if(field != null && !field.isEmpty()){
            return field.equals("orderNum") || field.equals("userUUID") ||
                    field.equals("createdAt") || field.equals("updatedAt") ||
                    field.equals("status");
        }
        return false;
    }

    public static boolean isValidSort(String sort){
        if(sort != null && !sort.isEmpty()){
            return sort.equals("asc") || sort.equals("desc");
        }

        return false;
    }

    public static <T> T getClassFromJson(Object res, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = objectMapper.writeValueAsString(res);
            return objectMapper.readValue(responseBody, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> T getClassFromBytes(byte[] bytes, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(bytes, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> getListFromJson(Object res, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = objectMapper.writeValueAsString(res);
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);

            return objectMapper.readValue(responseBody, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}