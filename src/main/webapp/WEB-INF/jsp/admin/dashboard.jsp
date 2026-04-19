<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/nav.jsp" %>
<div class="container mt-4">
  <h2 class="mb-4">Admin Dashboard</h2>
  <div class="row g-3">
    <div class="col-md-4">
      <div class="card text-white bg-primary">
        <div class="card-body">
          <h5 class="card-title">Total Users</h5>
          <p class="display-6">${userCount}</p>
          <a href="${pageContext.request.contextPath}/admin/users" class="text-white">Manage &#8594;</a>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <div class="card text-white bg-success">
        <div class="card-body">
          <h5 class="card-title">Total Events</h5>
          <p class="display-6">${eventCount}</p>
          <a href="${pageContext.request.contextPath}/admin/events" class="text-white">Manage &#8594;</a>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <div class="card text-white bg-warning">
        <div class="card-body">
          <h5 class="card-title">Categories</h5>
          <a href="${pageContext.request.contextPath}/admin/categories" class="text-white">Manage &#8594;</a>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
