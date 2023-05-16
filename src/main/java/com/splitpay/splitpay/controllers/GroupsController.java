package com.splitpay.splitpay.controllers;

import com.splitpay.splitpay.entities.Group;
import java.util.ArrayList;
import java.util.List;
import com.splitpay.splitpay.services.JDBC;

public class GroupsController {
    private static Group selectedGroup;

    // TODO: Load groups table from database
    private static List<Group> currentGroups = JDBC.getAllGroups();

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
}
