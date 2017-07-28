package com.example.demo.util;

/**
 * Created by mayurlathkar on 25/07/17.
 */

public class CustomErrorType {

    private String errorMessage;
    private static CustomErrorType customErrorType = null;

    public static CustomErrorType getErrorObject(String errorMessage) {
        if (customErrorType == null)
            return new CustomErrorType(errorMessage);
        else
            return customErrorType;
    }

    public CustomErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

