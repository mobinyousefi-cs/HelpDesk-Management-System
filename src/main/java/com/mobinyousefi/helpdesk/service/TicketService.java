package com.mobinyousefi.helpdesk.service;

import com.mobinyousefi.helpdesk.dao.TicketDao;
import com.mobinyousefi.helpdesk.model.Ticket;
import com.mobinyousefi.helpdesk.model.TicketPriority;
import com.mobinyousefi.helpdesk.model.TicketStatus;
import com.mobinyousefi.helpdesk.model.User;

import java.util.List;

/**
 * Project: Help Desk Management System
 * File: TicketService.java
 * Author: Mobin Yousefi (GitHub: github.com/mobinyousefi-cs)
 */
public class TicketService {

    private final TicketDao ticketDao;

    public TicketService() {
        this.ticketDao = new TicketDao();
    }

    public TicketService(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public void createTicket(String subject,
                             String description,
                             TicketPriority priority,
                             User creator) {
        Ticket t = new Ticket();
        t.setSubject(subject);
        t.setDescription(description);
        t.setPriority(priority);
        t.setStatus(TicketStatus.OPEN);
        t.setCreatedByUserId(creator.getId());
        ticketDao.createTicket(t);
    }

    public List<Ticket> getTicketsForUser(User user) {
        boolean isAgent = "AGENT".equalsIgnoreCase(user.getRole());
        return ticketDao.findTicketsForUser(user.getId(), isAgent);
    }

    public Ticket getTicketById(long id) {
        return ticketDao.findById(id);
    }

    public void updateStatus(long ticketId, TicketStatus status) {
        ticketDao.updateStatus(ticketId, status);
    }
}
