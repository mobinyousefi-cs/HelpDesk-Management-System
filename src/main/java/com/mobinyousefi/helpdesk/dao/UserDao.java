package com.mobinyousefi.helpdesk.dao;

import com.mobinyousefi.helpdesk.config.DBConnectionManager;
import com.mobinyousefi.helpdesk.model.User;

import java.sql.*;

/**
 * Project: Help Desk Management System
 * File: UserDao.java
 * Author: Mobin Yousefi (GitHub: github.com/mobinyousefi-cs)
 */
public class UserDao {

    private static final String SELECT_BY_USERNAME =
            "SELECT id, username, password_hash, full_name, email, role FROM users WHERE username = ?";

    public User findByUsername(String username) {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_USERNAME)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query user by username.", e);
        }
    }
}
