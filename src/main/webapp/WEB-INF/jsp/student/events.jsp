<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/nav.jsp" %>
<div class="container mt-4">
  <h2 class="mb-4">Browse Events</h2>

  <form method="get" action="${pageContext.request.contextPath}/events" class="card p-3 mb-4">
    <div class="row g-2">
      <div class="col-md-3">
        <input type="text" name="title" class="form-control" placeholder="Search by title"
               value="${param.title}">
      </div>
      <div class="col-md-2">
        <select name="type" class="form-select">
          <option value="">All Types</option>
          <option value="workshop"        ${param.type == 'workshop'        ? 'selected' : ''}>Workshop</option>
          <option value="seminar"         ${param.type == 'seminar'         ? 'selected' : ''}>Seminar</option>
          <option value="club_social"     ${param.type == 'club_social'     ? 'selected' : ''}>Club Social</option>
          <option value="sports_activity" ${param.type == 'sports_activity' ? 'selected' : ''}>Sports</option>
        </select>
      </div>
      <div class="col-md-2">
        <select name="category_id" class="form-select">
          <option value="">All Categories</option>
          <c:forEach var="cat" items="${categories}">
            <option value="${cat.id}" ${param.category_id == cat.id ? 'selected' : ''}>${cat.name}</option>
          </c:forEach>
        </select>
      </div>
      <div class="col-md-2">
        <input type="date" name="date" class="form-control" value="${param.date}">
      </div>
      <div class="col-md-2 d-flex align-items-center">
        <div class="form-check">
          <input class="form-check-input" type="checkbox" name="available" value="true"
                 id="avail" ${param.available == 'true' ? 'checked' : ''}>
          <label class="form-check-label" for="avail">Available only</label>
        </div>
      </div>
      <div class="col-md-1">
        <button type="submit" class="btn btn-primary w-100">Filter</button>
      </div>
    </div>
  </form>

  <div class="row g-3">
    <c:choose>
      <c:when test="${empty events}">
        <div class="col-12 text-center text-muted py-5">No events found.</div>
      </c:when>
      <c:otherwise>
        <c:forEach var="ev" items="${events}">
          <div class="col-md-4">
            <div class="card h-100 shadow-sm">
              <div class="card-body">
                <span class="badge bg-secondary mb-2">${ev.type}</span>
                <h5 class="card-title">${ev.title}</h5>
                <p class="card-text text-muted small">
                  <fmt:formatDate value="${ev.dateTime}" pattern="dd MMM yyyy HH:mm" type="both"/>
                </p>
                <p class="card-text small">${ev.location}</p>
                <p class="card-text small">Seats: <strong>${ev.seatsRemaining}</strong> / ${ev.capacity}</p>
                <c:if test="${ev.avgRating > 0}">
                  <p class="card-text small">Rating: <strong><fmt:formatNumber value="${ev.avgRating}" maxFractionDigits="1"/>/5</strong></p>
                </c:if>
              </div>
              <div class="card-footer">
                <a href="${pageContext.request.contextPath}/events/detail/${ev.id}"
                   class="btn btn-outline-primary btn-sm">View Details</a>
              </div>
            </div>
          </div>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
