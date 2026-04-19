<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/nav.jsp" %>
<div class="container mt-4">
  <h2 class="mb-4">Manage Events</h2>
  <div class="table-responsive">
    <table class="table table-bordered align-middle">
      <thead class="table-light">
        <tr><th>Title</th><th>Organizer</th><th>Date</th><th>Status</th><th>Actions</th></tr>
      </thead>
      <tbody>
        <c:forEach var="ev" items="${events}">
          <tr>
            <td>${ev.title}</td>
            <td>${ev.organizerName}</td>
            <td><fmt:formatDate value="${ev.dateTimeAsDate}" pattern="dd MMM yyyy" type="both"/></td>
            <td>
              <span class="badge ${ev.status == 'open' ? 'bg-success' : 'bg-secondary'}">${ev.status}</span>
            </td>
            <td>
              <form method="post" action="${pageContext.request.contextPath}/admin/events/status" style="display:inline">
                <input type="hidden" name="id" value="${ev.id}">
                <c:choose>
                  <c:when test="${ev.status == 'expired'}">
                    <span class="text-muted small">expired (auto)</span>
                  </c:when>
                  <c:otherwise>
                    <select name="status" class="form-select form-select-sm d-inline w-auto">
                      <option value="open"      ${ev.status=='open'      ? 'selected':''}>open</option>
                      <option value="closed"    ${ev.status=='closed'    ? 'selected':''}>closed</option>
                      <option value="completed" ${ev.status=='completed' ? 'selected':''}>completed</option>
                    </select>
                    <button class="btn btn-sm btn-outline-secondary">Set</button>
                  </c:otherwise>
                </c:choose>
              </form>
              <form method="post" action="${pageContext.request.contextPath}/admin/events/delete"
                    style="display:inline" onsubmit="return confirm('Delete event?')">
                <input type="hidden" name="id" value="${ev.id}">
                <button class="btn btn-sm btn-danger">Delete</button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
