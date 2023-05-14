package com.splitpay.splitpay.entities;

import java.util.List;

public class User {
    private String username;
    private String email;
    private int balance;
    private List<Group> groups;

    public User() {
    }

    public User(String username, String email, int balance) {
        this.username = username;
        this.email = email;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
