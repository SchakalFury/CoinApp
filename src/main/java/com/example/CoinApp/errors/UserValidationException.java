package com.example.CoinApp.errors;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }
}