package com.splitpay.splitpay.entities;

public class Member {
    private User user;
    private Group group;
    private double collectiveDebt;

    public Member(User user, Group group, double collectiveDebt) {
        this.user = user;
        this.group = group;
        this.collectiveDebt = collectiveDebt;
    }

    public Member(User user, Group group) {
        this.user = user;
        this.group = group;
        this.collectiveDebt = 0;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public double getCollectiveDebt() {
        return collectiveDebt;
    }

    public void setCollectiveDebt(double collectiveDebt) {
        this.collectiveDebt = collectiveDebt;
    }

    public String getGroupName() {
        return this.group.getName();
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public String toString() {
        return this.user.getUsername();
    }
}
