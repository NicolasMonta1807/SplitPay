package com.splitpay.splitpay.entities;

import java.util.Date;
import java.util.List;

public class Bill {
    private String name;
    private List<Debt> billDepts;
    private Date date;

    public Bill(String name, List<Debt> billDepts, Date date) {
        this.name = name;
        this.billDepts = billDepts;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Debt> getBillDepts() {
        return billDepts;
    }

    public void setBillDepts(List<Debt> billDepts) {
        this.billDepts = billDepts;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
