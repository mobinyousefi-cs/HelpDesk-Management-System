package com.mobinyousefi.helpdesk.model;

/**
 * Project: Help Desk Management System
 * File: User.java
 * Author: Mobin Yousefi (GitHub: github.com/mobinyousefi-cs)
 */
public class User {

    private long id;
    private String username;
    private String passwordHash; // you can store a hash instead of plain text
    private String fullName;
    private String email;
    private String role; // "CUSTOMER" or "AGENT"

    public User() {}

    public User(long id, String username, String passwordHash,
                String fullName, String email, String role) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    // Getters & setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
