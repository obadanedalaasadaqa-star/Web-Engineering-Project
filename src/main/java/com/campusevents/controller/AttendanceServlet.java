package com.campusevents.controller;

import com.campusevents.dao.ReservationDAO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AttendanceServlet extends HttpServlet {

    private final ReservationDAO resDAO = new ReservationDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String reservationId = req.getParameter("reservationId");
        String attendance    = req.getParameter("attendance");
        String eventId       = req.getParameter("eventId");

        try {
            resDAO.updateAttendance(reservationId, attendance);
        } catch (Exception e) {
            // Log silently
        }
        resp.sendRedirect(req.getContextPath() + "/organizer/events/attendees?id=" + eventId);
    }
}
