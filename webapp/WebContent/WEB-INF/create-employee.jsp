<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Enter Employee Details</h2>
	<form action="create-employee.do" method="POST">
      <div class="form-group">
        <label for="username">Username</label>
        <input type="text" class="form-control" id="username" name="username" placeholder="">
      </div>
      <div class="form-group">
        <label for="firstname">First Name</label>
        <input type="text" class="form-control" id="firstName" name="firstName" placeholder="">
      </div>
      <div class="form-group">
        <label for="lastname">Last Name</label>
        <input type="text" class="form-control" id="lastName" name="lastName" placeholder="">
      </div>
      <div class="form-group">
        <label for="password">Password </label>
        <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="">
      </div>
      <div class="form-group">
        <label for="password">Confirm Password </label>
        <input type="password" class="form-control" id="confirmPassword" name="confPassword" placeholder="">
      </div>
      <button type="submit" class="btn btn-primary">Create Employee Account</button>
    </form>
    <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
  </main>
<%@ include file="footer.jsp" %>