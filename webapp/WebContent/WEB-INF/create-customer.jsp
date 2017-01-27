<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Enter Customer Details</h2>
	<form action="create-customer.do" method="POST">
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
        <input type="text" class="form-control" id="lastname" name="lastName" placeholder="">
      </div>
      <div class="form-group">
        <label for="amount">Amount to be deposited(in dollars)</label>
        <div class="input-group">
          <div class="input-group-addon">$</div>
          <fmt:formatNumber var="maxAmountStr" value="${availableCash>1000000.00 ? 1000000.00 : availableCash}" groupingUsed="false" minFractionDigits="2" maxFractionDigits="2"/>
          <input type="number" class="form-control" id="amount" name="amount" placeholder="${maxAmountStr}" step="0.01" min="0.00" max="1000000.00" required>
        </div>
      </div>
      <div class="form-group">
        <label for="address">Address Line 1</label>
        <input type="text" class="form-control" id="address1" name="address1" placeholder="">
      </div>
      <div class="form-group">
        <label for="address2">Address Line 2</label>
        <input type="text" class="form-control" id="address2" name="address2" placeholder="">
      </div>
      <div class="form-group">
        <label for="address2">City</label>
        <input type="text" class="form-control" id="address2" name="address2" placeholder="">
      </div>
      <div class="form-group">
        <label for="address2">State</label>
        <input type="text" class="form-control" id="address2" name="address2" placeholder="">
      </div>
      <div class="form-group">
        <label for="address2">Zipcode</label>
        <input type="text" class="form-control" id="address2" name="address2" placeholder="">
      </div>
      <div class="form-group">
        <label for="state">State</label>
        <input type="text" class="form-control" id="state" name="state" placeholder="">
      </div>
      <div class="form-group">
        <label for="zipcode">Zip code</label>
        <input type="text" class="form-control" id="zipcode" name="zipcode" placeholder="">
      </div>
      <div class="form-group">
        <label for="city">City</label>
        <input type="text" class="form-control" id="city" name="city" placeholder="">
      </div>

      
      <div class="form-group">
        <label for="password">Password </label>
        <input type="password" class="form-control" id="password" name="password" placeholder="">
      </div>
      <div class="form-group">
        <label for="password">Confirm Password </label>
        <input type="password" class="form-control" id="confpassword" name="confpassword" placeholder="">
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