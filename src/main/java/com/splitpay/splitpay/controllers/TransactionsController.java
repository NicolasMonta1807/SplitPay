package com.splitpay.splitpay.controllers;

import com.splitpay.splitpay.entities.Member;
import com.splitpay.splitpay.entities.Transaction;
import com.splitpay.splitpay.entities.User;
import com.splitpay.splitpay.services.JDBC;

public class TransactionsController {
    private static Member fromMember;
    private static Member toMember;
    private static int amount;

    public static Member getFromMember() {
        return fromMember;
    }

    public static void setFromMember(Member fromMember) {
        TransactionsController.fromMember = fromMember;
    }

    public static Member getToMember() {
        return toMember;
    }

    public static void setToMember(Member toMember) {
        TransactionsController.toMember = toMember;
    }

    public static int getAmount() {
        return amount;
    }

    public static void setAmount(int amount) {
        TransactionsController.amount = amount;
    }

    public static int getDebt(Member fromUser, Member toUser) {
        return JDBC.getDebt(fromUser, toUser);
    }

    public static void performTransaction() {
        Transaction transaction = new Transaction(fromMember, toMember, amount);
        JDBC.performTransaction(transaction);
    }
}
