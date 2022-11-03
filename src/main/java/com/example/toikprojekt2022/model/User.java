package com.example.toikprojekt2022.model;


import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Klasa reprezentuje użytkownika
 */

@Entity
public class User {
    @Id
    private UUID userId;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String address;
    private String debitCardNumber;
    private String expireDate;
    private String cvv;
    private String email;
    @OneToMany(mappedBy = "cartOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CartItem> shoppingCartItems;

    public User(UUID userId, String login, String password,
                String name, String surname, String address,
                String debitCardNumber, String expireDate, String cvv, String email) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.debitCardNumber = debitCardNumber;
        this.expireDate = expireDate;
        this.cvv = cvv;
        this.email = email;
    }

    public User(String login, String password, String name,
                String surname, String address, String debitCardNumber,
                String expireDate, String cvv, String email) {
        this.userId = UUID.randomUUID();
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.debitCardNumber = debitCardNumber;
        this.expireDate = expireDate;
        this.cvv = cvv;
        this.email = email;

    }

    public User(User user) {
        this.userId=user.getUserId();
        this.login=user.getLogin();
        this.password=user.getPassword();
        this.name=user.getName();
        this.surname=user.getSurname();
        this.cvv=user.getCvv();
        this.address=user.getAddress();
        this.debitCardNumber=user.getDebitCardNumber();
        this.expireDate=user.getExpireDate();
        this.email=user.getEmail();
    }

    public User() {
        this.userId = UUID.randomUUID();
    }
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", debitCardNumber='" + debitCardNumber + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", cvv='" + cvv + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public User(UUID userId,
                String login,
                String password,
                String name,
                String surname,
                String address,
                String debitCardNumber,
                String expireDate,
                String cvv,
                String email,
                List<CartItem> shoppingCartItems) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.debitCardNumber = debitCardNumber;
        this.expireDate = expireDate;
        this.cvv = cvv;
        this.email = email;
        this.shoppingCartItems = shoppingCartItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDebitCardNumber() {
        return debitCardNumber;
    }

    public void setDebitCardNumber(String debitCardNumber) {
        this.debitCardNumber = debitCardNumber;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
