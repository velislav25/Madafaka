package com.example.friendfind.domain;

public class User {
    String uid;
    String email;


    public User(String email) {
        this.email = email;
    }

    public User(String email, String uid) {
        this.uid = uid;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
