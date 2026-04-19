package com.campusevents.controller;

import com.campusevents.dao.CategoryDAO;
import com.campusevents.dao.EventDAO;
import com.campusevents.dao.RatingDAO;
import com.campusevents.dao.ReservationDAO;
import com.campusevents.model.Event;
import com.campusevents.pattern.strategy.EventSearchContext;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class EventServlet extends HttpServlet {

    private final EventDAO eventDAO         = new EventDAO();
    private final CategoryDAO categoryDAO   = new CategoryDAO();
    private final ReservationDAO resDAO     = new ReservationDAO();
    private final RatingDAO ratingDAO       = new RatingDAO();
    private final EventSearchContext search = new EventSearchContext();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) path = "/list";

        if (path.equals("/list")) {
            showEventList(req, resp);
        } else if (path.startsWith("/detail/")) {
            showEventDetail(req, resp, path.substring("/detail/".length()));
        } else {
            resp.sendRedirect(req.getContextPath() + "/events");
        }
    }

    private void showEventList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            eventDAO.expireOverdueEvents();
            List<Event> all = eventDAO.findAll();

            Map<String, String> params = new HashMap<>();
            params.put("title",       req.getParameter("title"));
            params.put("department",  req.getParameter("department"));
            params.put("date",        req.getParameter("date"));
            params.put("category_id", req.getParameter("category_id"));
            params.put("type",        req.getParameter("type"));
            params.put("available",   req.getParameter("available"));

            List<Event> filtered = search.execute(all, params);

            req.setAttribute("events",     filtered);
            req.setAttribute("categories", categoryDAO.findAllCategories());
            req.getRequestDispatcher("/WEB-INF/jsp/student/events.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void showEventDetail(HttpServletRequest req, HttpServletResponse resp, String eventId)
            throws ServletException, IOException {
        try {
            Event event = eventDAO.findById(eventId);
            if (event == null) { resp.sendError(404); return; }

            String userId = (String) req.getSession().getAttribute("userId");
            boolean hasReservation = resDAO.hasReservation(userId, eventId);
            boolean hasRated       = ratingDAO.hasRated(userId, eventId);

            req.setAttribute("event",          event);
            req.setAttribute("hasReservation", hasReservation);
            req.setAttribute("hasRated",       hasRated);
            req.setAttribute("ratings",        ratingDAO.findByEvent(eventId));
            req.getRequestDispatcher("/WEB-INF/jsp/student/event-detail.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
