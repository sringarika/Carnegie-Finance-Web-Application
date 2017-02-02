<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>
 <main>
    <h2>Request Check</h2>
        <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
    <c:if test="${availableCash <= 0}">
      <div class="alert alert-info">
        You don't have any available cash to withdraw.
      </div>
    </c:if>
    <c:if test="${availableCash > 0}">
      <form action="request-check.do" method="POST">
      <div>
          <label for="cash">Available Cash:</label>
          <p><fmt:formatNumber value="${availableCash}" type="currency"/></p>
      </div>
  
      <div class="form-group">
          <label for="amount">Amount to Withdraw (in dollars)</label>
          <div class="input-group">
          <div class="input-group-addon">$</div>
          <fmt:formatNumber var="maxAmountStr" value="${availableCash}" groupingUsed="false" minFractionDigits="2" maxFractionDigits="2"/>
          <fmt:formatNumber var="maxAmountDisp" value="${availableCash}" type="currency"/>
          <input type="number" class="form-control" id="amount" name="amount" placeholder="$0.01 ~ ${maxAmountDisp}" step="0.01" min="0.01" max="${maxAmountStr}" required>
          </div>
      </div>
      <div class="alert alert-info" role="alert">
          The amount you want to withdraw cannot exceed the amount of your available cash.
      </div>
      <button type="submit" class="btn btn-primary">Submit Request</button>
      </form>
    </c:if>
  </main>
<%@ include file="footer.jsp" %>
