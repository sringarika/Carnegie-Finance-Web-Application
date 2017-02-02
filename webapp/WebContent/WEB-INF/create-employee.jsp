<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Enter Employee Details</h2>
    <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
    <form action="create-employee.do" method="POST">
      <div class="form-group">
        <label for="username">Username</label>
        <input type="text" class="form-control" id="username" name="username" maxlength="20" required value="${fn:escapeXml(username)}">
      </div>
      <div class="form-group">
        <label for="firstname">First Name</label>
        <input type="text" class="form-control" id="firstname" name="firstname" maxlength="40" required value="${fn:escapeXml(firstname)}">
      </div>
      <div class="form-group">
        <label for="lastname">Last Name</label>
        <input type="text" class="form-control" id="lastname" name="lastname" maxlength="40" required value="${fn:escapeXml(lastname)}">
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" name="password" maxlength="100" required>
      </div>
      <div class="form-group">
        <label for="password">Confirm Password </label>
        <input type="password" class="form-control" id="confirmpassword" name="confirmpassword" maxlength="100" required>
      </div>
      <button type="submit" class="btn btn-primary">Create Employee Account</button>
    </form>
  </main>
<%@ include file="footer.jsp" %>