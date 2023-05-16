package com.splitpay.splitpay.entities;

import java.util.List;
import java.util.Objects;

public class User {
    private String username;
    private String email;
    private String phone;

    public User() {
    }

    public User(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if(!(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        return username.equals(other.username) && email.equals(other.email) && phone.equals(other.phone);
    }
}
