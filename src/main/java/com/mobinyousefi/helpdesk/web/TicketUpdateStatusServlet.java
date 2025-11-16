package com.mobinyousefi.helpdesk.web;

import com.mobinyousefi.helpdesk.model.TicketStatus;
import com.mobinyousefi.helpdesk.model.User;
import com.mobinyousefi.helpdesk.service.TicketService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Project: Help Desk Management System
 * File: TicketUpdateStatusServlet.java
 * Author: Mobin Yousefi (GitHub: github.com/mobinyousefi-cs)
 */
@WebServlet(name = "TicketUpdateStatusServlet", urlPatterns = "/tickets/updateStatus")
public class TicketUpdateStatusServlet extends HttpServlet {

    private TicketService ticketService;

    @Override
    public void init() {
        this.ticketService = new TicketService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User currentUser = (User) session.getAttribute("currentUser");
        boolean isAgent = "AGENT".equalsIgnoreCase(currentUser.getRole());
        if (!isAgent) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Only agents can update ticket status.");
            return;
        }

        long ticketId = Long.parseLong(req.getParameter("ticketId"));
        TicketStatus newStatus = TicketStatus.valueOf(req.getParameter("status"));

        ticketService.updateStatus(ticketId, newStatus);
        resp.sendRedirect(req.getContextPath() + "/tickets/detail?id=" + ticketId);
    }
}
