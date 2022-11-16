package com.example.toikprojekt2022.exception;

public class RestarurantNotFoundException extends RuntimeException {
    public RestarurantNotFoundException(String message) {
        super(message);
    }
}
