package com.splitpay.splitpay.services;

import com.splitpay.splitpay.entities.Group;
import com.splitpay.splitpay.entities.Member;
import com.splitpay.splitpay.entities.User;

import java.util.ArrayList;
import java.util.List;

public class JDBC {
    public static List<Member> getAllMembers() {
        // TODO: Load members table from database
        return new ArrayList<>() {
            {
                add(new Member(new User("Nikoresu", "correo", "123"), new Group("Bases"), 25));
                add(new Member(new User("Gabriel", "correo", "456"), new Group("Bases"), -50));
                add(new Member(new User("JuanJo", "correo", "789"), new Group("Bases"), 25));
            }
        };
    }

    public static List<User> getAllUsers() {
        return new ArrayList<>() {
            {
                add(new User("Nikoresu", "correo", "123"));
                add(new User("Gabriel", "correo", "456"));
                add(new User("Juanjo", "correo", "789"));
            }
        };
    }

    public static void createUser(User userToCreate) {
        System.out.println("User should be created: " + userToCreate.getUsername());
    }
}
