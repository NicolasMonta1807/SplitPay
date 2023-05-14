package com.splitpay.splitpay.entities;

import java.util.List;

public class Group {
    private String name;
    private User leader;
    private List<User> members;
    public Group() {
    }

    public Group(String name, User leader) {
        this.name = name;
        this.leader = leader;
    }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }
}
