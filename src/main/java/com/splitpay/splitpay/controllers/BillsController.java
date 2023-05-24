package com.splitpay.splitpay.controllers;

import com.splitpay.splitpay.entities.Bill;
import com.splitpay.splitpay.entities.Debt;
import com.splitpay.splitpay.services.JDBC;

import java.sql.SQLException;
import java.util.Map;

public class BillsController {
    private static Map<String, Map<String, Integer>> report;

    public static void createBill(Bill billToCreate) throws SQLException {
        JDBC.createBill(billToCreate, GroupsController.getSelectedGroup());
        for (Debt debt : billToCreate.getBillDepts()) {
            JDBC.createDebt(billToCreate, debt);
        }
    }

    public static void loadReport() throws SQLException {
        report = JDBC.getReports(UsersController.getLoggedUser());
    }

    public static Map<String, Map<String, Integer>> getReport() throws SQLException {
        loadReport();
        return report;
    }

    public static void setReport(Map<String, Map<String, Integer>> report) {
        BillsController.report = report;
    }
}
