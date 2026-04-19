<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/nav.jsp" %>
<div class="container mt-4">
  <h2 class="mb-1">${event.title}</h2>
  <p class="text-muted mb-4">${reservations.size()} attendee(s)</p>

  <div class="table-responsive">
    <table class="table table-bordered align-middle">
      <thead class="table-light">
        <tr><th>Name</th><th>Uni Number</th><th>Email</th><th>Attendance</th><th>Mark</th></tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${empty reservations}">
            <tr><td colspan="5" class="text-center text-muted">No reservations yet.</td></tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="res" items="${reservations}">
              <tr>
                <td>${res.user.name}</td>
                <td>${not empty res.user.uniNumber ? res.user.uniNumber : '—'}</td>
                <td>${res.user.email}</td>
                <td>
                  <span class="badge ${res.attendance == 'present' ? 'bg-success' : res.attendance == 'absent' ? 'bg-danger' : 'bg-secondary'}">
                    ${res.attendance}
                  </span>
                </td>
                <td>
                  <form method="post" action="${pageContext.request.contextPath}/organizer/attendance" style="display:inline">
                    <input type="hidden" name="reservationId" value="${res.id}">
                    <input type="hidden" name="eventId"       value="${event.id}">
                    <input type="hidden" name="attendance"    value="present">
                    <button class="btn btn-sm btn-success">Present</button>
                  </form>
                  <form method="post" action="${pageContext.request.contextPath}/organizer/attendance" style="display:inline">
                    <input type="hidden" name="reservationId" value="${res.id}">
                    <input type="hidden" name="eventId"       value="${event.id}">
                    <input type="hidden" name="attendance"    value="absent">
                    <button class="btn btn-sm btn-danger">Absent</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
  </div>
  <a href="${pageContext.request.contextPath}/organizer/dashboard" class="btn btn-secondary">Back</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
