package com.splitpay.splitpay.controllers;

import com.splitpay.splitpay.entities.User;
import com.splitpay.splitpay.services.JDBC;

public class TransactionsController {
    private static User fromUser;
    private static User toUser;
    private static int amount;

    public static User getFromUser() {
        return fromUser;
    }

    public static void setFromUser(User fromUser) {
        TransactionsController.fromUser = fromUser;
    }

    public static User getToUser() {
        return toUser;
    }

    public static void setToUser(User toUser) {
        TransactionsController.toUser = toUser;
    }

    public static int getAmount() {
        return amount;
    }

    public static void setAmount(int amount) {
        TransactionsController.amount = amount;
    }

    public static int getDebt(User fromUser, User toUser) {
        return JDBC.getDebt(fromUser, toUser);
    }

    public static void performTransaction() {
        JDBC.performTransaction(fromUser, toUser, amount);
    }
}
