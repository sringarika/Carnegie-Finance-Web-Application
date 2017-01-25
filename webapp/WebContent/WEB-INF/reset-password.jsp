<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
  <h2>Reset Password for</h2>
  <tr>
      <td>Name</td>
      <td>${firstName} ${lastName}</td>
    </tr>
    <h2>Reset Password</h2>
	<form action="reset-password.do" method="POST">
      <input type="hidden" name="customerId" value="${fn:escapeXml(customerId)}">
      <div class="form-group">
        <label for="password">Enter Customer's New Password</label>
        <input type="password" class="form-control" id="password" name="newPassword" placeholder="">
      </div>
      <div class="form-group">
        <label for="confirmPassword">Confirm New Password</label>
        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="">
      </div>
      <button type="submit" class="btn btn-primary">Reset Password</button>
    </form>
    <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
  </main>
<%@ include file="footer.jsp" %>