<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.getSession().removeAttribute("userType"); %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Welcome to CFS Mutual Funds</h2>
    <img src="mutual_funds.png" alt="welcome page" style="width:80%;height:100%;">
    <h3> </h3>
    <form action="account.jsp" method="POST">
      <div class="form-group">
        <label for="username">Username</label>
        <input type="text" class="form-control" id="username" placeholder="">
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" placeholder="">
      </div>
      <button type="submit" class="btn btn-default">Customer Login</button>
      <a href="employee.jsp" class="btn btn-default">Employee Login</a>
    </form>
    <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
  </main>
<%@ include file="footer.jsp" %>