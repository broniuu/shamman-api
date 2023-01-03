package com.example.toikprojekt2022.dto;
/**
 * rekord używany do przekazywania informaij logowania.
 * @param login     login użytkownika
 * @param password      haslo użytkownika
 * */
public record LoginRequest(String login, String password) {

}
