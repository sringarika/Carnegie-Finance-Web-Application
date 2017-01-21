<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Enter Customer Details</h2>
	<form action="create-customer-success.jsp" method="POST">
      <div class="form-group">
        <label for="name">Name</label>
        <input type="text" class="form-control" id="name" placeholder="">
      </div>
      <div class="form-group">
        <label for="username">Username</label>
        <input type="text" class="form-control" id="username" placeholder="">
      </div>
      <div class="form-group">
        <label for="address">Current Address</label>
        <input type="text" class="form-control" id="address" placeholder="">
      </div>
      <div class="form-group">
        <label for="password">Password </label>
        <input type="password" class="form-control" id="password" placeholder="">
      </div>
      <div class="form-group">
        <label for="password">Confirm Password </label>
        <input type="password" class="form-control" id="confpassword" placeholder="">
      </div>
      <div class="form-group">
        <label for="deposit">Initial Deposit </label>
        <div class="input-group">
        <div class="input-group-addon">$</div>
        <input type="number" class="form-control" id="deposit" placeholder="1,000.00" required>
        </div>
      </div>
      <button type="submit" class="btn btn-primary">Create Customer Account</button>
    </form>
    <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
  </main>
<%@ include file="footer.jsp" %>