<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/nav.jsp" %>
<div class="container mt-4">
  <h2 class="mb-4">Manage Users</h2>
  <div class="table-responsive">
    <table class="table table-bordered align-middle">
      <thead class="table-light">
        <tr><th>Name</th><th>Email</th><th>Role</th><th>Status</th><th>Actions</th></tr>
      </thead>
      <tbody>
        <c:forEach var="u" items="${users}">
          <tr>
            <td>${u.name}</td>
            <td>${u.email}</td>
            <td><span class="badge bg-secondary">${u.role}</span></td>
            <td>
              <span class="badge ${u.status == 'active' ? 'bg-success' : 'bg-danger'}">${u.status}</span>
            </td>
            <td>
              <c:choose>
                <c:when test="${u.id == sessionScope.userId}">
                  <span class="text-muted small">—</span>
                </c:when>
                <c:otherwise>
                  <c:if test="${u.status == 'active'}">
                    <form method="post" action="${pageContext.request.contextPath}/admin/users/block" style="display:inline">
                      <input type="hidden" name="id" value="${u.id}">
                      <button class="btn btn-sm btn-warning">Block</button>
                    </form>
                  </c:if>
                  <c:if test="${u.status == 'blocked'}">
                    <form method="post" action="${pageContext.request.contextPath}/admin/users/unblock" style="display:inline">
                      <input type="hidden" name="id" value="${u.id}">
                      <button class="btn btn-sm btn-success">Unblock</button>
                    </form>
                  </c:if>
                  <form method="post" action="${pageContext.request.contextPath}/admin/users/delete"
                        style="display:inline" onsubmit="return confirm('Delete user ${u.name}?')">
                    <input type="hidden" name="id" value="${u.id}">
                    <button class="btn btn-sm btn-danger">Delete</button>
                  </form>
                </c:otherwise>
              </c:choose>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
