package com.campusevents.controller;

import com.campusevents.dao.CategoryDAO;
import com.campusevents.dao.EventDAO;
import com.campusevents.dao.ReservationDAO;
import com.campusevents.model.Event;
import com.campusevents.pattern.factory.EventFactory;
import com.campusevents.pattern.factory.EventFactoryProvider;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.OffsetDateTime;

public class OrganizerServlet extends HttpServlet {

    private final EventDAO eventDAO       = new EventDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final ReservationDAO resDAO   = new ReservationDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null) path = "/dashboard";
        String organizerId = (String) req.getSession().getAttribute("userId");

        try {
            switch (path) {
                case "/dashboard":
                    req.setAttribute("events", eventDAO.findByOrganizer(organizerId));
                    req.getRequestDispatcher("/WEB-INF/jsp/organizer/dashboard.jsp").forward(req, resp);
                    break;
                case "/events/create":
                    req.setAttribute("categories", categoryDAO.findAllCategories());
                    req.getRequestDispatcher("/WEB-INF/jsp/organizer/create-event.jsp").forward(req, resp);
                    break;
                case "/events/edit":
                    String editId = req.getParameter("id");
                    Event ev = eventDAO.findById(editId);
                    if (ev == null || !ev.getOrganizerId().equals(organizerId)) {
                        resp.sendError(403); return;
                    }
                    req.setAttribute("event", ev);
                    req.setAttribute("categories", categoryDAO.findAllCategories());
                    req.getRequestDispatcher("/WEB-INF/jsp/organizer/edit-event.jsp").forward(req, resp);
                    break;
                case "/events/attendees":
                    String attId = req.getParameter("id");
                    Event attEv = eventDAO.findById(attId);
                    if (attEv == null || !attEv.getOrganizerId().equals(organizerId)) {
                        resp.sendError(403); return;
                    }
                    req.setAttribute("event",        attEv);
                    req.setAttribute("reservations", resDAO.findByEvent(attId));
                    req.getRequestDispatcher("/WEB-INF/jsp/organizer/attendees.jsp").forward(req, resp);
                    break;
                default:
                    resp.sendRedirect(req.getContextPath() + "/organizer/dashboard");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();
        String organizerId = (String) req.getSession().getAttribute("userId");

        try {
            if ("/events/create".equals(path)) {
                String type        = req.getParameter("type");
                String title       = req.getParameter("title");
                String description = req.getParameter("description");
                String categoryId  = req.getParameter("category_id");
                String dateTime    = req.getParameter("date_time");
                String location    = req.getParameter("location");
                int    capacity    = Integer.parseInt(req.getParameter("capacity"));
                String department  = req.getParameter("department");

                EventFactory factory = EventFactoryProvider.getFactory(type);
                Event event = factory.create(title, description, organizerId,
                                             categoryId, dateTime + ":00+03:00",
                                             location, capacity, department);
                eventDAO.create(event);
                resp.sendRedirect(req.getContextPath() + "/organizer/dashboard");

            } else if ("/events/edit".equals(path)) {
                String eventId = req.getParameter("id");
                Event existing = eventDAO.findById(eventId);
                if (existing == null || !existing.getOrganizerId().equals(organizerId)) {
                    resp.sendError(403); return;
                }
                existing.setTitle(req.getParameter("title"));
                existing.setDescription(req.getParameter("description"));
                existing.setCategoryId(req.getParameter("category_id"));
                existing.setType(req.getParameter("type"));
                existing.setDateTime(OffsetDateTime.parse(req.getParameter("date_time") + ":00+03:00"));
                existing.setLocation(req.getParameter("location"));
                existing.setCapacity(Integer.parseInt(req.getParameter("capacity")));
                existing.setDepartment(req.getParameter("department"));
                existing.setStatus(req.getParameter("status"));
                eventDAO.update(existing);
                resp.sendRedirect(req.getContextPath() + "/organizer/dashboard");

            } else if ("/events/delete".equals(path)) {
                String eventId = req.getParameter("id");
                Event existing = eventDAO.findById(eventId);
                if (existing != null && existing.getOrganizerId().equals(organizerId)) {
                    eventDAO.delete(eventId);
                }
                resp.sendRedirect(req.getContextPath() + "/organizer/dashboard");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
