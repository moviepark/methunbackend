package com.example.demo.exception;

public class ShiftLimitExceededException extends RuntimeException {
    public ShiftLimitExceededException(String message) {
        super(message);
    }
}
