package com.splitpay.splitpay.controllers;

import com.splitpay.splitpay.entities.Group;
import com.splitpay.splitpay.entities.Member;
import com.splitpay.splitpay.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MembersController {

    // TODO: Load members table from database
    private static List<Member> allCurrentMembers = new ArrayList<>() {
        {
            add(new Member(new User("Nikoresu", "correo", 0), new Group("Londres"), 0));
            add(new Member(new User("Gabriel", "correo", 0), new Group("Londres"), 100));
            add(new Member(new User("Pablo", "correo", 0), new Group("Londres"), -50));
            add(new Member(new User("Juanfe", "correo", 0), new Group("Londres"), -50));
        }
    };

    public MembersController() {
        allCurrentMembers.add(new Member(new User("Nikoresu", "correo", 0), new Group("Londres"), 0));
    }

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
}
