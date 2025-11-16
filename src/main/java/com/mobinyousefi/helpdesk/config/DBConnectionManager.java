package com.mobinyousefi.helpdesk.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Project: Help Desk Management System
 * File: DBConnectionManager.java
 * Author: Mobin Yousefi (GitHub: github.com/mobinyousefi-cs)
 *
 * Simple connection manager using DriverManager.
 * In production you should use a real connection pool (HikariCP, etc.).
 */
@WebListener
public class DBConnectionManager implements ServletContextListener {

    private static String jdbcUrl;
    private static String jdbcUser;
    private static String jdbcPassword;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        jdbcUrl = ctx.getInitParameter("JDBC_URL");
        jdbcUser = ctx.getInitParameter("JDBC_USER");
        jdbcPassword = ctx.getInitParameter("JDBC_PASSWORD");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ctx.log("MySQL JDBC driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            ctx.log("Failed to load MySQL JDBC driver.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // no-op
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
    }
}
