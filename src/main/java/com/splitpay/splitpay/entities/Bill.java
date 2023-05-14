package com.splitpay.splitpay.entities;

import javafx.scene.image.Image;

import java.util.Date;
import java.util.List;

public class Bill {
    private String name;
    private double cost;
    private Group group;
    private User creator;
    private List<User> involvedMembers;
    private Date date;

    public Bill() {
    }

    public Bill(String name, double cost, Group group, User creator, List<User> involvedMembers, Date date) {
        this.name = name;
        this.cost = cost;
        this.group = group;
        this.creator = creator;
        this.involvedMembers = involvedMembers;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getInvolvedMembers() {
        return involvedMembers;
    }

    public void setInvolvedMembers(List<User> involvedMembers) {
        this.involvedMembers = involvedMembers;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
