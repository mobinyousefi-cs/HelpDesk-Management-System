package com.mobinyousefi.helpdesk.dao;

import com.mobinyousefi.helpdesk.config.DBConnectionManager;
import com.mobinyousefi.helpdesk.model.Ticket;
import com.mobinyousefi.helpdesk.model.TicketPriority;
import com.mobinyousefi.helpdesk.model.TicketStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: Help Desk Management System
 * File: TicketDao.java
 * Author: Mobin Yousefi (GitHub: github.com/mobinyousefi-cs)
 */
public class TicketDao {

    private static final String INSERT_SQL = """
            INSERT INTO tickets (subject, description, status, priority, created_by_user_id,
                                 assigned_to_user_id, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SELECT_ALL_BY_USER = """
            SELECT * FROM tickets
            WHERE created_by_user_id = ?
            ORDER BY created_at DESC
            """;

    private static final String SELECT_ALL = """
            SELECT * FROM tickets
            ORDER BY created_at DESC
            """;

    private static final String SELECT_BY_ID = "SELECT * FROM tickets WHERE id = ?";

    private static final String UPDATE_STATUS = """
            UPDATE tickets
            SET status = ?, updated_at = ?
            WHERE id = ?
            """;

    public void createTicket(Ticket ticket) {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ticket.getSubject());
            ps.setString(2, ticket.getDescription());
            ps.setString(3, ticket.getStatus().name());
            ps.setString(4, ticket.getPriority().name());
            ps.setLong(5, ticket.getCreatedByUserId());

            if (ticket.getAssignedToUserId() != null) {
                ps.setLong(6, ticket.getAssignedToUserId());
            } else {
                ps.setNull(6, Types.BIGINT);
            }

            Timestamp nowTs = Timestamp.valueOf(LocalDateTime.now());
            ps.setTimestamp(7, nowTs);
            ps.setTimestamp(8, nowTs);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    ticket.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert ticket.", e);
        }
    }

    public List<Ticket> findTicketsForUser(long userId, boolean isAgent) {
        String sql = isAgent ? SELECT_ALL : SELECT_ALL_BY_USER;

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (!isAgent) {
                ps.setLong(1, userId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                List<Ticket> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query tickets.", e);
        }
    }

    public Ticket findById(long id) {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query ticket by id.", e);
        }
    }

    public void updateStatus(long ticketId, TicketStatus status) {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_STATUS)) {

            ps.setString(1, status.name());
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(3, ticketId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update ticket status.", e);
        }
    }

    private Ticket mapRow(ResultSet rs) throws SQLException {
        Ticket t = new Ticket();
        t.setId(rs.getLong("id"));
        t.setSubject(rs.getString("subject"));
        t.setDescription(rs.getString("description"));
        t.setStatus(TicketStatus.valueOf(rs.getString("status")));
        t.setPriority(TicketPriority.valueOf(rs.getString("priority")));
        t.setCreatedByUserId(rs.getLong("created_by_user_id"));

        long assigned = rs.getLong("assigned_to_user_id");
        if (!rs.wasNull()) {
            t.setAssignedToUserId(assigned);
        }

        Timestamp created = rs.getTimestamp("created_at");
        Timestamp updated = rs.getTimestamp("updated_at");
        if (created != null) {
            t.setCreatedAt(created.toLocalDateTime());
        }
        if (updated != null) {
            t.setUpdatedAt(updated.toLocalDateTime());
        }
        return t;
    }
}
