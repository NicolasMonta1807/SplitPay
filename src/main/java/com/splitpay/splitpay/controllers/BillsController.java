package com.splitpay.splitpay.controllers;

import com.splitpay.splitpay.entities.Bill;
import com.splitpay.splitpay.entities.Debt;
import com.splitpay.splitpay.services.JDBC;

public class BillsController {
    public static void createBill(Bill billToCreate) {
        System.out.println("Factura creada: ");
        for(Debt debt: billToCreate.getBillDepts()) {
            System.out.println("\t" + debt.getOwingName()  + " debe a " + debt.getCreditor().getUserName() + ": " + debt.getDebtCost());
        }
        System.out.println(billToCreate.getDate());
        JDBC.createBill(billToCreate);
    }
}
