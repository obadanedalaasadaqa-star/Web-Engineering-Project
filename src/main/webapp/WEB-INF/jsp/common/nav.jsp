<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container">
    <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">CampusEvents</a>
    <div class="navbar-nav ms-auto">
      <c:choose>
        <c:when test="${sessionScope.role == 'student'}">
          <a class="nav-link" href="${pageContext.request.contextPath}/events">Events</a>
          <a class="nav-link" href="${pageContext.request.contextPath}/reservations">My Reservations</a>
        </c:when>
        <c:when test="${sessionScope.role == 'organizer'}">
          <a class="nav-link" href="${pageContext.request.contextPath}/organizer/dashboard">My Events</a>
        </c:when>
        <c:when test="${sessionScope.role == 'admin'}">
          <a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
          <a class="nav-link" href="${pageContext.request.contextPath}/admin/users">Users</a>
          <a class="nav-link" href="${pageContext.request.contextPath}/admin/events">Events</a>
          <a class="nav-link" href="${pageContext.request.contextPath}/admin/categories">Categories</a>
        </c:when>
      </c:choose>
      <c:if test="${not empty sessionScope.userId}">
        <span class="nav-link text-light opacity-75">Hello, ${sessionScope.name}</span>
        <a class="nav-link" href="${pageContext.request.contextPath}/auth/logout">Logout</a>
      </c:if>
    </div>
  </div>
</nav>
