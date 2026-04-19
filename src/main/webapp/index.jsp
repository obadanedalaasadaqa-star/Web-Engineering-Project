<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String userId = (String) session.getAttribute("userId");
    String role   = (String) session.getAttribute("role");
    if (userId == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login");
    } else if ("organizer".equals(role)) {
        response.sendRedirect(request.getContextPath() + "/organizer/dashboard");
    } else if ("admin".equals(role)) {
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    } else {
        response.sendRedirect(request.getContextPath() + "/events");
    }
%>
