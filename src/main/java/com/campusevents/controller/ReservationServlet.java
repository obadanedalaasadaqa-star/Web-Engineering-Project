package com.campusevents.controller;

import com.campusevents.dao.ReservationDAO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class ReservationServlet extends HttpServlet {

    private final ReservationDAO resDAO = new ReservationDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = (String) req.getSession().getAttribute("userId");
        try {
            req.setAttribute("reservations", resDAO.findByUser(userId));
            req.getRequestDispatcher("/WEB-INF/jsp/student/reservations.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path    = req.getPathInfo();
        String userId  = (String) req.getSession().getAttribute("userId");
        String eventId = req.getParameter("eventId");

        try {
            if ("/create".equals(path)) {
                resDAO.reserve(userId, eventId);
            } else if ("/cancel".equals(path)) {
                resDAO.cancel(userId, eventId);
            }
        } catch (Exception e) {
            req.getSession().setAttribute("flashError", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/events/detail/" + eventId);
    }
}
