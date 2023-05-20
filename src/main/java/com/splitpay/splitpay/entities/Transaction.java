package com.splitpay.splitpay.entities;

public class Transaction {
    private Member fromMember;
    private Member toMember;
    private int amount;

    public Transaction(Member fromMember, Member toMember, int amount) {
        this.fromMember = fromMember;
        this.toMember = toMember;
        this.amount = amount;
    }

    public Member getFromMember() {
        return fromMember;
    }

    public void setFromMember(Member fromMember) {
        this.fromMember = fromMember;
    }

    public Member getToMember() {
        return toMember;
    }

    public void setToMember(Member toMember) {
        this.toMember = toMember;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
