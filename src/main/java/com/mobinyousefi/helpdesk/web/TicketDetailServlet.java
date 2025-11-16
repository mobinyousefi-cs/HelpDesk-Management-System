package com.mobinyousefi.helpdesk.web;

import com.mobinyousefi.helpdesk.model.Ticket;
import com.mobinyousefi.helpdesk.model.TicketStatus;
import com.mobinyousefi.helpdesk.model.User;
import com.mobinyousefi.helpdesk.service.TicketService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Project: Help Desk Management System
 * File: TicketDetailServlet.java
 * Author: Mobin Yousefi (GitHub: github.com/mobinyousefi-cs)
 */
@WebServlet(name = "TicketDetailServlet", urlPatterns = "/tickets/detail")
public class TicketDetailServlet extends HttpServlet {

    private TicketService ticketService;

    @Override
    public void init() {
        this.ticketService = new TicketService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String idParam = req.getParameter("id");
        long id = Long.parseLong(idParam);

        Ticket ticket = ticketService.getTicketById(id);
        if (ticket == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Ticket not found.");
            return;
        }

        User currentUser = (User) session.getAttribute("currentUser");
        boolean isAgent = "AGENT".equalsIgnoreCase(currentUser.getRole());
        boolean isOwner = ticket.getCreatedByUserId() == currentUser.getId();

        if (!isAgent && !isOwner) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "You cannot view this ticket.");
            return;
        }

        req.setAttribute("ticket", ticket);
        req.setAttribute("statuses", TicketStatus.values());
        req.getRequestDispatcher("/WEB-INF/views/ticket_detail.jsp").forward(req, resp);
    }
}
