package com.campusevents.controller;

import com.campusevents.dao.CategoryDAO;
import com.campusevents.dao.EventDAO;
import com.campusevents.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AdminServlet extends HttpServlet {

    private final UserDAO     userDAO     = new UserDAO();
    private final EventDAO    eventDAO    = new EventDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null) path = "/dashboard";

        try {
            eventDAO.expireOverdueEvents();
            switch (path) {
                case "/dashboard":
                    req.setAttribute("userCount",  userDAO.findAll().size());
                    req.setAttribute("eventCount", eventDAO.findAll().size());
                    req.getRequestDispatcher("/WEB-INF/jsp/admin/dashboard.jsp").forward(req, resp);
                    break;
                case "/users":
                    req.setAttribute("users", userDAO.findAll());
                    req.getRequestDispatcher("/WEB-INF/jsp/admin/users.jsp").forward(req, resp);
                    break;
                case "/events":
                    req.setAttribute("events", eventDAO.findAll());
                    req.getRequestDispatcher("/WEB-INF/jsp/admin/events.jsp").forward(req, resp);
                    break;
                case "/categories":
                    req.setAttribute("categories", categoryDAO.findAllCategories());
                    req.getRequestDispatcher("/WEB-INF/jsp/admin/categories.jsp").forward(req, resp);
                    break;
                default:
                    resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();

        try {
            switch (path) {
                case "/users/block": {
                    String targetId = req.getParameter("id");
                    String selfId   = (String) req.getSession().getAttribute("userId");
                    if (!targetId.equals(selfId))
                        userDAO.updateStatus(targetId, "blocked");
                    resp.sendRedirect(req.getContextPath() + "/admin/users");
                    break;
                }
                case "/users/unblock":
                    userDAO.updateStatus(req.getParameter("id"), "active");
                    resp.sendRedirect(req.getContextPath() + "/admin/users");
                    break;
                case "/users/delete": {
                    String targetId = req.getParameter("id");
                    String selfId   = (String) req.getSession().getAttribute("userId");
                    if (!targetId.equals(selfId))
                        userDAO.delete(targetId);
                    resp.sendRedirect(req.getContextPath() + "/admin/users");
                    break;
                }
                case "/events/delete":
                    eventDAO.delete(req.getParameter("id"));
                    resp.sendRedirect(req.getContextPath() + "/admin/events");
                    break;
                case "/events/status": {
                    String newStatus = req.getParameter("status");
                    if (!"expired".equals(newStatus))
                        eventDAO.updateStatus(req.getParameter("id"), newStatus);
                    resp.sendRedirect(req.getContextPath() + "/admin/events");
                    break;
                }
                case "/categories/create":
                    categoryDAO.createCategory(req.getParameter("name"));
                    resp.sendRedirect(req.getContextPath() + "/admin/categories");
                    break;
                case "/categories/delete":
                    categoryDAO.deleteCategory(req.getParameter("id"));
                    resp.sendRedirect(req.getContextPath() + "/admin/categories");
                    break;
                default:
                    resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
