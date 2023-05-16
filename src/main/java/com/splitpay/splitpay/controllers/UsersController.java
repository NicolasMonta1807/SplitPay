package com.splitpay.splitpay.controllers;

import com.splitpay.splitpay.entities.User;
import com.splitpay.splitpay.services.JDBC;

import java.util.List;

public class UsersController {
    public static User loggedUser;
    public static List<User> allCurrentUsers = JDBC.getAllUsers();

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User userToLog) {
        UsersController.loggedUser = userToLog;
    }

    public static void createAndSetLoggedUser(User userToLog) {
        allCurrentUsers.add(userToLog);
        JDBC.createUser(userToLog);
        setLoggedUser(userToLog);
    }

    public static List<User> getAllCurrentUsers() {
        return allCurrentUsers;
    }

    public static void setAllCurrentUsers(List<User> allCurrentUsers) {
        UsersController.allCurrentUsers = allCurrentUsers;
    }

    public static boolean checkExistingUser(User userToCheck) {
        return allCurrentUsers.contains(userToCheck);
    }
}