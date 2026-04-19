<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/nav.jsp" %>
<div class="container mt-4">
  <h2 class="mb-4">My Reservations</h2>
  <c:choose>
    <c:when test="${empty reservations}">
      <div class="text-center text-muted py-5">You have no active reservations.</div>
    </c:when>
    <c:otherwise>
      <div class="table-responsive">
        <table class="table table-bordered align-middle">
          <thead class="table-light">
            <tr>
              <th>Event</th>
              <th>Date &amp; Time</th>
              <th>Location</th>
              <th>Attendance</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="res" items="${reservations}">
              <tr>
                <td>
                  <a href="${pageContext.request.contextPath}/events/detail/${res.eventId}">
                    ${res.event.title}
                  </a>
                </td>
                <td>
                  <fmt:formatDate value="${res.event.dateTime}" pattern="dd MMM yyyy HH:mm" type="both"/>
                </td>
                <td>${res.event.location}</td>
                <td>
                  <span class="badge ${res.attendance == 'present' ? 'bg-success' : res.attendance == 'absent' ? 'bg-danger' : 'bg-secondary'}">
                    ${res.attendance}
                  </span>
                </td>
                <td>
                  <form method="post" action="${pageContext.request.contextPath}/reservations/cancel">
                    <input type="hidden" name="eventId" value="${res.eventId}">
                    <button class="btn btn-sm btn-outline-danger"
                            onclick="return confirm('Cancel this reservation?')">Cancel</button>
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
