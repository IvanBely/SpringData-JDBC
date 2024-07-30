package com.example.SpringData_JDBC.exception;

public class InvalidBookException extends RuntimeException {

    public InvalidBookException(String message) {
        super(message);
    }
}