<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/nav.jsp" %>
<div class="container mt-4">
  <div class="row justify-content-center">
    <div class="col-md-7">
      <h2 class="mb-4">Edit Event</h2>
      <form method="post" action="${pageContext.request.contextPath}/organizer/events/edit">
        <input type="hidden" name="id" value="${event.id}">
        <div class="mb-3">
          <label class="form-label">Event Type</label>
          <select name="type" class="form-select" required>
            <option value="workshop"        ${event.type == 'workshop'        ? 'selected' : ''}>Workshop</option>
            <option value="seminar"         ${event.type == 'seminar'         ? 'selected' : ''}>Seminar</option>
            <option value="club_social"     ${event.type == 'club_social'     ? 'selected' : ''}>Club Social</option>
            <option value="sports_activity" ${event.type == 'sports_activity' ? 'selected' : ''}>Sports Activity</option>
          </select>
        </div>
        <div class="mb-3">
          <label class="form-label">Title</label>
          <input type="text" name="title" class="form-control" value="${event.title}" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Description</label>
          <textarea name="description" class="form-control" rows="3" required>${event.description}</textarea>
        </div>
        <div class="mb-3">
          <label class="form-label">Category</label>
          <select name="category_id" class="form-select">
            <option value="">-- No Category --</option>
            <c:forEach var="cat" items="${categories}">
              <option value="${cat.id}" ${event.categoryId == cat.id ? 'selected' : ''}>${cat.name}</option>
            </c:forEach>
          </select>
        </div>
        <div class="mb-3">
          <label class="form-label">Date &amp; Time</label>
          <input type="datetime-local" name="date_time" class="form-control" required
                 value="<fmt:formatDate value='${event.dateTimeAsDate}' pattern='yyyy-MM-dd HH:mm' type='both'/>">
        </div>
        <div class="mb-3">
          <label class="form-label">Location</label>
          <input type="text" name="location" class="form-control" value="${event.location}" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Capacity</label>
          <input type="number" name="capacity" class="form-control" value="${event.capacity}" min="1" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Department</label>
          <input type="text" name="department" class="form-control" value="${event.department}">
        </div>
        <div class="mb-3">
          <label class="form-label">Status</label>
          <select name="status" class="form-select">
            <option value="open"      ${event.status == 'open'      ? 'selected' : ''}>Open</option>
            <option value="closed"    ${event.status == 'closed'    ? 'selected' : ''}>Closed</option>
            <option value="completed" ${event.status == 'completed' ? 'selected' : ''}>Completed</option>
          </select>
        </div>
        <button type="submit" class="btn btn-primary">Save Changes</button>
        <a href="${pageContext.request.contextPath}/organizer/dashboard" class="btn btn-secondary ms-2">Cancel</a>
      </form>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
