package com.mobinyousefi.helpdesk.service;

import com.mobinyousefi.helpdesk.dao.UserDao;
import com.mobinyousefi.helpdesk.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Project: Help Desk Management System
 * File: AuthService.java
 * Author: Mobin Yousefi (GitHub: github.com/mobinyousefi-cs)
 */
public class AuthService {

    private final UserDao userDao;

    public AuthService() {
        this.userDao = new UserDao();
    }

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Authenticate by username and plain password.
     * For demo purposes this uses SHA-256.
     */
    public User authenticate(String username, String plainPassword) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return null;
        }
        String hashed = hashPassword(plainPassword);
        if (!hashed.equals(user.getPasswordHash())) {
            return null;
        }
        return user;
    }

    public String hashPassword(String plainPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available.", e);
        }
    }
}
