package com.umass.hangout.user_registeration.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

    // Generic method to convert any object to JSON string
    public <T> String toJson(T object) {
        try {
            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert the Java object to JSON string
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Handle exceptions as needed
        }
    }

}