<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-md-6">
      <div class="card shadow">
        <div class="card-body p-4">
          <h2 class="card-title mb-4">Create Account</h2>
          <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
          </c:if>
          <form method="post" action="${pageContext.request.contextPath}/auth/register" id="regForm">
            <div class="mb-3">
              <label class="form-label">Full Name</label>
              <input type="text" name="name" class="form-control" required minlength="2">
            </div>
            <div class="mb-3">
              <label class="form-label">Email</label>
              <input type="email" name="email" class="form-control" required>
            </div>
            <div class="mb-3">
              <label class="form-label">Password</label>
              <input type="password" name="password" class="form-control" required minlength="8">
            </div>
            <div class="mb-3">
              <label class="form-label">Role</label>
              <select name="role" class="form-select" id="roleSelect" required onchange="toggleRoleFields()">
                <option value="student">Student</option>
                <option value="organizer">Organizer</option>
              </select>
            </div>
            <div class="mb-3">
              <label class="form-label">Admission Year</label>
              <input type="number" name="admission_year" class="form-control" required
                     min="1990" max="2026" value="2024">
            </div>

            <div id="studentFields">
              <div class="mb-3">
                <label class="form-label">Major (optional)</label>
                <input type="text" name="major" class="form-control">
              </div>
            </div>

            <div id="organizerFields" style="display:none">
              <div class="mb-3">
                <label class="form-label">Faculty</label>
                <select name="faculty" class="form-select" id="facultySelect" onchange="loadDepartments()">
                  <option value="">-- Select Faculty --</option>
                  <c:forEach var="f" items="${faculties}">
                    <option value="${f.name}" data-id="${f.id}">${f.name}</option>
                  </c:forEach>
                </select>
              </div>
              <div class="mb-3">
                <label class="form-label">Department</label>
                <select name="department" class="form-select" id="departmentSelect">
                  <option value="">-- Select Department --</option>
                </select>
              </div>
            </div>

            <button type="submit" class="btn btn-primary w-100">Register</button>
          </form>
          <p class="mt-3 text-center">
            Already have an account? <a href="${pageContext.request.contextPath}/auth/login">Login</a>
          </p>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
function toggleRoleFields() {
  var role = document.getElementById('roleSelect').value;
  document.getElementById('studentFields').style.display   = role === 'student'   ? '' : 'none';
  document.getElementById('organizerFields').style.display = role === 'organizer' ? '' : 'none';
}
function loadDepartments() {
  var select = document.getElementById('facultySelect');
  var opt = select.options[select.selectedIndex];
  var facultyId = opt.getAttribute('data-id');
  var deptSelect = document.getElementById('departmentSelect');
  deptSelect.innerHTML = '<option value="">Loading...</option>';
  if (!facultyId) return;
  fetch('${pageContext.request.contextPath}/auth/departments?facultyId=' + facultyId)
    .then(function(r) { return r.json(); })
    .then(function(data) {
      deptSelect.innerHTML = '<option value="">-- Select Department --</option>';
      data.forEach(function(d) {
        deptSelect.innerHTML += '<option value="' + d.name + '">' + d.name + '</option>';
      });
    });
}
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
