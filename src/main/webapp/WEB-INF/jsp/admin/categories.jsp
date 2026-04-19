<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/nav.jsp" %>
<div class="container mt-4">
  <h2 class="mb-4">Manage Categories</h2>

  <form method="post" action="${pageContext.request.contextPath}/admin/categories/create" class="mb-4">
    <div class="input-group" style="max-width:400px">
      <input type="text" name="name" class="form-control" placeholder="New category name" required>
      <button class="btn btn-primary">Add</button>
    </div>
  </form>

  <ul class="list-group" style="max-width:400px">
    <c:forEach var="cat" items="${categories}">
      <li class="list-group-item d-flex justify-content-between align-items-center">
        ${cat.name}
        <form method="post" action="${pageContext.request.contextPath}/admin/categories/delete"
              onsubmit="return confirm('Delete category?')">
          <input type="hidden" name="id" value="${cat.id}">
          <button class="btn btn-sm btn-outline-danger">Delete</button>
        </form>
      </li>
    </c:forEach>
  </ul>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
