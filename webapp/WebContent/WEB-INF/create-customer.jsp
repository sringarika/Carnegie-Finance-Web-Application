<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Enter Customer Details</h2>
    <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
    <form action="create-customer.do" method="POST">
      <div class="form-group">
        <label for="username">Username</label>
        <input type="text" class="form-control" id="username" name="username" required maxlength="20" value="${fn:escapeXml(username)}">
      </div>
      <div class="form-group">
        <label for="firstname">First Name</label>
        <input type="text" class="form-control" id="firstName" name="firstName" required maxlength="20" value="${fn:escapeXml(firstName)}">
      </div>
      <div class="form-group">
        <label for="lastname">Last Name</label>
        <input type="text" class="form-control" id="lastName" name="lastName" required maxlength="20" value="${fn:escapeXml(lastName)}">
      </div>
      <div class="form-group">
        <label for="amount">Amount to Be Deposited (in dollars)</label>
        <div class="input-group">
          <div class="input-group-addon">$</div>
          <input type="number" class="form-control" id="amount" name="amount" placeholder="$0.00 ~ $1,000,000.00" value="${fn:escapeXml(amount)}" step="0.01" min="0.00" max="1000000.00" required>
        </div>
      </div>
      <div class="form-group">
        <label for="address">Address Line 1</label>
        <input type="text" class="form-control" id="address1" name="address1" required maxlength="40" value="${fn:escapeXml(address1)}">
      </div>
      <div class="form-group">
        <label for="address2">Address Line 2</label>
        <input type="text" class="form-control" id="address2" name="address2" maxlength="40" value="${fn:escapeXml(address2)}">
      </div>
      <div class="form-group">
        <label for="state">State</label>
        <input type="text" class="form-control" id="state" name="state" required maxlength="40" value="${fn:escapeXml(state)}">
      </div>
      <div class="form-group">
        <label for="zipcode">Zip code</label>
        <input type="text" class="form-control" id="zipcode" name="zipcode" required maxlength="10" value="${fn:escapeXml(zipcode)}">
      </div>
      <div class="form-group">
        <label for="city">City</label>
        <input type="text" class="form-control" id="city" name="city" required maxlength="40" value="${fn:escapeXml(city)}">
      </div>
      
      <div class="form-group">
        <label for="password">Password </label>
        <input type="password" class="form-control" id="password" name="password" maxlength="100" required>
      </div>
      <div class="form-group">
        <label for="password">Confirm Password </label>
        <input type="password" class="form-control" id="confpassword" name="confpassword" maxlength="100" required>
      </div>
      <button type="submit" class="btn btn-primary">Create Customer Account</button>
    </form>
  </main>
<%@ include file="footer.jsp" %>