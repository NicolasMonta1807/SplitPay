package com.splitpay.splitpay.services;

import com.splitpay.splitpay.entities.Bill;
import com.splitpay.splitpay.entities.Group;
import com.splitpay.splitpay.entities.Member;
import com.splitpay.splitpay.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JDBC {
    public static List<Member> getAllMembers() {
        // TODO: Load members table from database
        return new ArrayList<>() {
            {
                add(new Member(new User("Nikoresu", "correo", "123"), new Group("Bases"), 0));
                add(new Member(new User("Gabriel", "correo", "456"), new Group("Bases"), 0));
                add(new Member(new User("JuanJo", "correo", "789"), new Group("Bases"), 0));
                add(new Member(new User("Nikoresu", "correo", "123"), new Group("Londres"), 0));
                add(new Member(new User("Gabriel", "correo", "456"), new Group("Londres"), 0));
            }
        };
    }

    public static List<User> getAllUsers() {
        // TODO: Load Users table from database
        System.out.println("Users requested");
        return new ArrayList<>() {
            {
                add(new User("Nikoresu", "correo", "123"));
                add(new User("Gabriel", "correo", "456"));
                add(new User("Juanjo", "correo", "789"));
            }
        };
    }

    public static List<Group> getAllGroups() {
        // TODO: Load groups table from database
        return new ArrayList<>() {
            {
                add(new Group("Bases"));
                add(new Group("Londres"));
            }
        };
    }

    public static void createUser(User userToCreate) {
        // TODO: Insert new user into database
        System.out.println("User should be created: " + userToCreate.getUsername());
    }

    public static void createBill(Bill bill) {
        // TODO: Add the bill to database and update members balances (Transaction)
    }

    public static int getDebt(User fromUser, User toUser) {
        // TODO: Calculate the debt that fromUser ows toUser
        return new Random().nextInt(1001);
    }

    public static void performTransaction(User fromUser, User toUser, int amount) {
        // TODO: Update users balances (Transaction)
        System.out.println("Transaction completed: " + fromUser.getUsername() + " has sent " + amount + " to " + toUser.getUsername());
    }

    public static void createGroup(Group groupToCreate){
        // TODO: Insert new group to database
        System.out.println("Group should be created: " + groupToCreate.getName());
    }

    public static void createMember(Member memberToCreate) {
        // TODO: Insert new member to database
        System.out.println("User " + memberToCreate.getUserName() + " should be added as member of " + memberToCreate.getGroupName());
    }
}
