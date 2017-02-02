<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Change Password</h2>
    <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
    <form action="change-password.do" method="POST">
      <div class="form-group">
        <label for="oldPassword">Current Password</label>
        <input type="password" class="form-control" id="oldPassword" name="oldPassword" placeholder="">
      </div>
      <div class="form-group">
        <label for="newPassword">New Password</label>
        <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="">
      </div>
      <div class="form-group">
        <label for="confirmPassword">Confirm New Password</label>
        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="">
      </div>
      <button type="submit" class="btn btn-primary">Change Password</button>
    </form>
  </main>
<%@ include file="footer.jsp" %>
