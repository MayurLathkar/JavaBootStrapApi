package com.example.demo.util;

/**
 * Created by mayurlathkar on 26/07/17.
 */
public class CustomSuccessMessage {

    private String successMessage;
    private static CustomSuccessMessage customSuccessMessage = null;

    public static CustomSuccessMessage getSuccessObject(String successMessage) {
        if (customSuccessMessage == null)
            return new CustomSuccessMessage(successMessage);
        else
            return customSuccessMessage;
    }

    public CustomSuccessMessage(String errorMessage) {
        this.successMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }
}
