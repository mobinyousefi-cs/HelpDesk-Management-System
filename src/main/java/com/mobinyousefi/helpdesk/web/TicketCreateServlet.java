package com.mobinyousefi.helpdesk.web;

import com.mobinyousefi.helpdesk.model.TicketPriority;
import com.mobinyousefi.helpdesk.model.User;
import com.mobinyousefi.helpdesk.service.TicketService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Project: Help Desk Management System
 * File: TicketCreateServlet.java
 * Author: Mobin Yousefi (GitHub: github.com/mobinyousefi-cs)
 */
@WebServlet(name = "TicketCreateServlet", urlPatterns = "/tickets/new")
public class TicketCreateServlet extends HttpServlet {

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

        req.setAttribute("priorities", TicketPriority.values());
        req.getRequestDispatcher("/WEB-INF/views/ticket_new.jsp").forward(req, resp);
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

        String subject = req.getParameter("subject");
        String description = req.getParameter("description");
        String priorityStr = req.getParameter("priority");

        TicketPriority priority = TicketPriority.valueOf(priorityStr);

        ticketService.createTicket(subject, description, priority, currentUser);

        resp.sendRedirect(req.getContextPath() + "/tickets");
    }
}
