package com.splitpay.splitpay.controllers;

import com.splitpay.splitpay.entities.Group;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.splitpay.splitpay.entities.Member;
import com.splitpay.splitpay.entities.User;
import com.splitpay.splitpay.services.JDBC;

public class GroupsController {
    private static Group selectedGroup;
    private static List<Group> currentGroups = null;

    public static void loadGroups() throws SQLException {
        currentGroups = JDBC.getAllGroups();
    }

    public static Group getSelectedGroup() {
        return selectedGroup;
    }

    public static void setSelectedGroup(String  name) {
        GroupsController.selectedGroup = findGroupByName(name);
    }

    private static Group findGroupByName(String name) {
        for (Group group: currentGroups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }

    public static void createGroup(String groupName, User leader, List<User> groupMembers) throws SQLException {
        Group groupToCreate = new Group(groupName, leader);
        JDBC.createGroup(groupToCreate);
        currentGroups.add(groupToCreate);
        for(User user: groupMembers) {
            MembersController.newMember(new Member(user, groupToCreate));
        }
    }
}
