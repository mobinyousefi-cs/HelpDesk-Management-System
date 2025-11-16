package com.mobinyousefi.helpdesk.web;

import com.mobinyousefi.helpdesk.model.Ticket;
import com.mobinyousefi.helpdesk.model.User;
import com.mobinyousefi.helpdesk.service.TicketService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Project: Help Desk Management System
 * File: TicketListServlet.java
 * Author: Mobin Yousefi (GitHub: github.com/mobinyousefi-cs)
 */
@WebServlet(name = "TicketListServlet", urlPatterns = "/tickets")
public class TicketListServlet extends HttpServlet {

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

        User currentUser = (User) session.getAttribute("currentUser");
        List<Ticket> tickets = ticketService.getTicketsForUser(currentUser);

        req.setAttribute("tickets", tickets);
        req.getRequestDispatcher("/WEB-INF/views/tickets.jsp").forward(req, resp);
    }
}
