<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/nav.jsp" %>
<div class="container mt-4">
  <div class="row">
    <div class="col-md-8">
      <span class="badge bg-secondary mb-2">${event.type}</span>
      <c:if test="${not empty event.categoryName}">
        <span class="badge bg-info text-dark mb-2">${event.categoryName}</span>
      </c:if>
      <h2>${event.title}</h2>
      <p class="text-muted">
        <fmt:formatDate value="${event.dateTime}" pattern="dd MMMM yyyy 'at' HH:mm" type="both"/>
        &nbsp;|&nbsp; ${event.location}
      </p>
      <p>${event.description}</p>
      <p>Organised by: <strong>${event.organizerName}</strong></p>
      <p>Seats remaining: <strong>${event.seatsRemaining}</strong> / ${event.capacity}</p>

      <c:choose>
        <c:when test="${hasReservation}">
          <form method="post" action="${pageContext.request.contextPath}/reservations/cancel">
            <input type="hidden" name="eventId" value="${event.id}">
            <button class="btn btn-danger">Cancel Reservation</button>
          </form>
        </c:when>
        <c:when test="${event.seatsRemaining > 0 && event.status == 'open'}">
          <form method="post" action="${pageContext.request.contextPath}/reservations/create">
            <input type="hidden" name="eventId" value="${event.id}">
            <button class="btn btn-primary">Reserve a Seat</button>
          </form>
        </c:when>
        <c:otherwise>
          <button class="btn btn-secondary" disabled>No seats available</button>
        </c:otherwise>
      </c:choose>
    </div>

    <div class="col-md-4">
      <div class="card">
        <div class="card-header">
          Ratings
          <c:if test="${event.avgRating > 0}">
            (<fmt:formatNumber value="${event.avgRating}" maxFractionDigits="1"/>/5)
          </c:if>
        </div>
        <div class="card-body">
          <c:if test="${hasReservation && !hasRated}">
            <form method="post" action="${pageContext.request.contextPath}/ratings/submit" class="mb-3">
              <input type="hidden" name="eventId" value="${event.id}">
              <div class="mb-2">
                <label class="form-label">Stars (1-5)</label>
                <input type="number" name="stars" class="form-control" min="1" max="5" required>
              </div>
              <div class="mb-2">
                <label class="form-label">Review</label>
                <textarea name="review" class="form-control" rows="2" required></textarea>
              </div>
              <button class="btn btn-outline-warning btn-sm">Submit Rating</button>
            </form>
          </c:if>

          <c:forEach var="r" items="${ratings}">
            <div class="border-bottom pb-2 mb-2">
              <strong>${r.userName}</strong>
              <span class="text-warning">${r.stars}&#9733;</span>
              <p class="mb-0 small">${r.review}</p>
            </div>
          </c:forEach>
          <c:if test="${empty ratings}">
            <p class="text-muted small">No reviews yet.</p>
          </c:if>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
