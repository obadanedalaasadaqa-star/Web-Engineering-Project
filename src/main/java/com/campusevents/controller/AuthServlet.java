package com.campusevents.controller;

import com.campusevents.dao.CategoryDAO;
import com.campusevents.dao.UserDAO;
import com.campusevents.model.User;
import com.campusevents.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null) path = "/";

        switch (path) {
            case "/login":
                req.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(req, resp);
                break;
            case "/register":
                try {
                    req.setAttribute("faculties", categoryDAO.findAllFaculties());
                } catch (Exception e) {
                    req.setAttribute("faculties", java.util.Collections.emptyList());
                }
                req.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(req, resp);
                break;
            case "/logout":
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + "/auth/login");
                break;
            case "/departments":
                String facultyId = req.getParameter("facultyId");
                try {
                    java.util.List<com.campusevents.model.Department> depts =
                        categoryDAO.findDepartmentsByFaculty(facultyId);
                    StringBuilder json = new StringBuilder("[");
                    for (int i = 0; i < depts.size(); i++) {
                        if (i > 0) json.append(",");
                        json.append("{\"id\":\"").append(depts.get(i).getId())
                            .append("\",\"name\":\"").append(depts.get(i).getName()).append("\"}");
                    }
                    json.append("]");
                    resp.setContentType("application/json");
                    resp.getWriter().write(json.toString());
                } catch (Exception e) {
                    resp.setStatus(500);
                }
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/auth/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();
        if ("/login".equals(path)) handleLogin(req, resp);
        else if ("/register".equals(path)) handleRegister(req, resp);
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String email    = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = userDAO.findByEmail(email);
            if (user == null || !PasswordUtil.verify(password, user.getPasswordHash())) {
                req.setAttribute("error", "Invalid email or password");
                req.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(req, resp);
                return;
            }
            if ("blocked".equals(user.getStatus())) {
                req.setAttribute("error", "Your account has been blocked");
                req.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(req, resp);
                return;
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("role",   user.getRole());
            session.setAttribute("name",   user.getName());

            String redirect;
            switch (user.getRole()) {
                case "organizer": redirect = "/organizer/dashboard"; break;
                case "admin":     redirect = "/admin/dashboard";     break;
                default:          redirect = "/events";              break;
            }
            resp.sendRedirect(req.getContextPath() + redirect);
        } catch (Exception e) {
            req.setAttribute("error", "Login failed. Please try again.");
            req.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(req, resp);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String name       = req.getParameter("name");
        String email      = req.getParameter("email");
        String password   = req.getParameter("password");
        String role       = req.getParameter("role");
        String faculty    = req.getParameter("faculty");
        String department = req.getParameter("department");
        String major      = req.getParameter("major");
        String admYearStr = req.getParameter("admission_year");

        try {
            if (name == null || name.length() < 2) {
                req.setAttribute("error", "Name must be at least 2 characters");
                req.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(req, resp);
                return;
            }
            if (password == null || password.length() < 8) {
                req.setAttribute("error", "Password must be at least 8 characters");
                req.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(req, resp);
                return;
            }
            if ("organizer".equals(role) && (faculty == null || faculty.isEmpty()
                    || department == null || department.isEmpty())) {
                req.setAttribute("error", "Faculty and department are required for organizers");
                req.setAttribute("faculties", categoryDAO.findAllFaculties());
                req.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(req, resp);
                return;
            }
            if (userDAO.emailExists(email)) {
                req.setAttribute("error", "Email already in use");
                req.setAttribute("faculties", categoryDAO.findAllFaculties());
                req.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(req, resp);
                return;
            }

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPasswordHash(password);
            user.setRole(role);
            user.setFaculty("organizer".equals(role) ? faculty : null);
            user.setDepartment("organizer".equals(role) ? department : null);
            user.setMajor("student".equals(role) ? major : null);
            user.setAdmissionYear(Integer.parseInt(admYearStr));
            userDAO.create(user);

            User created = userDAO.findByEmail(email);
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", created.getId());
            session.setAttribute("role",   created.getRole());
            session.setAttribute("name",   created.getName());

            resp.sendRedirect(req.getContextPath() +
                ("organizer".equals(role) ? "/organizer/dashboard" : "/events"));
        } catch (Exception e) {
            req.setAttribute("error", "Registration failed: " + e.getMessage());
            try { req.setAttribute("faculties", categoryDAO.findAllFaculties()); } catch (Exception ignored) {}
            req.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(req, resp);
        }
    }
}
