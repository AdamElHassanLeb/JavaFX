package com.example.demo.Utils;
public class StringParsing {


    public static String extractErrorMessage(String errorMessage) {
        // Split the error message by single quote
        String[] parts = errorMessage.split("'");
            // Check if the error message contains "for key"
        if (parts.length > 1) {
                // Return the relevant part without "for key"
            return parts[1];
        } else {
            // If "for key" is not found, return the original error message
            return errorMessage;
        }
    }
}
