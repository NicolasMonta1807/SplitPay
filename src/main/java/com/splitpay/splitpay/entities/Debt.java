package com.splitpay.splitpay.entities;

public class Debt {
    private Member owing;
    private Member creditor;
    private int debtCost;

    public Debt(Member owing, Member creditor, int debtCost) {
        this.owing = owing;
        this.creditor = creditor;
        this.debtCost = debtCost;
    }

    public Member getOwing() {
        return owing;
    }

    public void setOwing(Member owing) {
        this.owing = owing;
    }

    public Member getCreditor() {
        return creditor;
    }

    public void setCreditor(Member creditor) {
        this.creditor = creditor;
    }

    public int getDebtCost() {
        return debtCost;
    }

    public void setDebtCost(int debtCost) {
        this.debtCost = debtCost;
    }

    public String getOwingName() {
        return this.owing.getUsername();
    }

    public String getOwingEmail() {
        return this.owing.getUser().getEmail();
    }
}
