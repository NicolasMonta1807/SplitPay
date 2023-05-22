package com.splitpay.splitpay.controllers;

import com.splitpay.splitpay.entities.Group;
import com.splitpay.splitpay.entities.Member;
import com.splitpay.splitpay.entities.User;
import com.splitpay.splitpay.services.JDBC;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MembersController {
    private static List<Member> allCurrentMembers = JDBC.getAllMembers();

    public static List<Member> getGroupsOfUser(String username) {
        List<Member> membersToReturn = new ArrayList<>();
        for (Member member: allCurrentMembers) {
            if (member.getUser().getUsername().equals(username)) {
                membersToReturn.add(member);
            }
        }
        return membersToReturn;
    }

    public static List<Member> getMembersOfGroup(String name) {
        List<Member> membersToReturn = new ArrayList<>();
        for(Member member: allCurrentMembers) {
            if (member.getGroupName().equals(name)) {
                membersToReturn.add(member);
            }
        }
        return membersToReturn;
    }

    public static void newMember(Member memberToAdd) {
        JDBC.createMember(memberToAdd);
        allCurrentMembers.add(memberToAdd);
    }

    public static void reloadMembers() {
        allCurrentMembers = JDBC.getAllMembers();
    }
}
