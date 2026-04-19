package com.campusevents.filter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();

        boolean loggedIn = (session != null && session.getAttribute("userId") != null);

        if (uri.startsWith(contextPath + "/auth/") ||
            uri.startsWith(contextPath + "/css/") ||
            uri.startsWith(contextPath + "/js/") ||
            uri.equals(contextPath + "/") ||
            uri.equals(contextPath + "/index.jsp")) {
            chain.doFilter(req, res);
            return;
        }

        if (!loggedIn) {
            response.sendRedirect(contextPath + "/auth/login");
            return;
        }

        String role = (String) session.getAttribute("role");

        if (uri.startsWith(contextPath + "/organizer/") && !"organizer".equals(role)) {
            response.sendRedirect(contextPath + "/events");
            return;
        }
        if (uri.startsWith(contextPath + "/admin/") && !"admin".equals(role)) {
            response.sendRedirect(contextPath + "/events");
            return;
        }

        chain.doFilter(req, res);
    }

    @Override public void init(FilterConfig fc) {}
    @Override public void destroy() {}
}
