package com.splitpay.splitpay.controllers;

import com.splitpay.splitpay.entities.Bill;
import com.splitpay.splitpay.entities.Debt;
import com.splitpay.splitpay.services.JDBC;

import java.sql.SQLException;

public class BillsController {
    public static void createBill(Bill billToCreate) throws SQLException {
        System.out.println("Factura creada: ");
        System.out.println(billToCreate.getDate());
        JDBC.createBill(billToCreate);
        for(Debt debt: billToCreate.getBillDepts()) {
            System.out.println("\t" + debt.getOwingName()  + " debe a " + debt.getCreditor().getUsername() + ": " + debt.getDebtCost());
            JDBC.createDebt(billToCreate, debt);
        }
    }
}
