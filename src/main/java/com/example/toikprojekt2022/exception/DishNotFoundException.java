package com.example.toikprojekt2022.exception;
/**
 * wyjątek używany gdy danie nie jest znalezione w bazie danych
 * */
public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException(String message) {
        super(message);
    }
}
