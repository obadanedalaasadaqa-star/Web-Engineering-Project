<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/nav.jsp" %>
<div class="container mt-4">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h2>My Events</h2>
    <a href="${pageContext.request.contextPath}/organizer/events/create" class="btn btn-primary">
      + Create Event
    </a>
  </div>

  <c:choose>
    <c:when test="${empty events}">
      <div class="text-center text-muted py-5">No events yet. Create your first one!</div>
    </c:when>
    <c:otherwise>
      <div class="table-responsive">
        <table class="table table-bordered align-middle">
          <thead class="table-light">
            <tr>
              <th>Title</th><th>Type</th><th>Date</th><th>Seats</th><th>Status</th><th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="ev" items="${events}">
              <tr>
                <td>${ev.title}</td>
                <td><span class="badge bg-secondary">${ev.type}</span></td>
                <td><fmt:formatDate value="${ev.dateTimeAsDate}" pattern="dd MMM yyyy" type="both"/></td>
                <td>${ev.seatsRemaining}/${ev.capacity}</td>
                <td>
                  <span class="badge ${ev.status == 'open' ? 'bg-success' : 'bg-warning text-dark'}">
                    ${ev.status}
                  </span>
                </td>
                <td>
                  <a href="${pageContext.request.contextPath}/organizer/events/edit?id=${ev.id}"
                     class="btn btn-sm btn-outline-secondary">Edit</a>
                  <a href="${pageContext.request.contextPath}/organizer/events/attendees?id=${ev.id}"
                     class="btn btn-sm btn-outline-primary">Attendees</a>
                  <form method="post" action="${pageContext.request.contextPath}/organizer/events/delete"
                        style="display:inline" onsubmit="return confirm('Delete this event?')">
                    <input type="hidden" name="id" value="${ev.id}">
                    <button class="btn btn-sm btn-outline-danger">Delete</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </c:otherwise>
  </c:choose>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
