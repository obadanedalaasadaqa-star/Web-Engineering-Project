<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/nav.jsp" %>
<div class="container mt-4">
  <div class="row justify-content-center">
    <div class="col-md-7">
      <h2 class="mb-4">Create New Event</h2>
      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>
      <form method="post" action="${pageContext.request.contextPath}/organizer/events/create">
        <div class="mb-3">
          <label class="form-label">Event Type</label>
          <select name="type" class="form-select" required>
            <option value="workshop">Workshop</option>
            <option value="seminar">Seminar</option>
            <option value="club_social">Club Social</option>
            <option value="sports_activity">Sports Activity</option>
          </select>
        </div>
        <div class="mb-3">
          <label class="form-label">Title</label>
          <input type="text" name="title" class="form-control" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Description</label>
          <textarea name="description" class="form-control" rows="3" required></textarea>
        </div>
        <div class="mb-3">
          <label class="form-label">Category</label>
          <select name="category_id" class="form-select">
            <option value="">-- No Category --</option>
            <c:forEach var="cat" items="${categories}">
              <option value="${cat.id}">${cat.name}</option>
            </c:forEach>
          </select>
        </div>
        <div class="mb-3">
          <label class="form-label">Date &amp; Time</label>
          <input type="datetime-local" name="date_time" class="form-control" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Location</label>
          <input type="text" name="location" class="form-control" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Capacity</label>
          <input type="number" name="capacity" class="form-control" min="1" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Department (optional)</label>
          <input type="text" name="department" class="form-control">
        </div>
        <button type="submit" class="btn btn-primary">Create Event</button>
        <a href="${pageContext.request.contextPath}/organizer/dashboard" class="btn btn-secondary ms-2">Cancel</a>
      </form>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
