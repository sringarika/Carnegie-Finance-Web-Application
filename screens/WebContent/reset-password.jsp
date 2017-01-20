<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Reset Password</h2>
	<form action="pwd-change-success.jsp" method="POST">
      
      <div class="form-group">
        <label for="password">Enter Customer's New Password</label>
        <input type="password" class="form-control" id="password" placeholder="">
      </div>
      <div class="form-group">
        <label for="password">Confirm New Password</label>
        <input type="password" class="form-control" id="password" placeholder="">
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