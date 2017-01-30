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
    <form action="request-check.do" method="POST">
    <div>
        <label for="cash">Available Cash:</label>
        <p><fmt:formatNumber value="${availableCash}" type="currency"/></p>
    </div>

    <div class="form-group">
        <label for="amount">Amount to withdraw (in dollars)</label>
        <div class="input-group">
        <div class="input-group-addon">$</div>
        <fmt:formatNumber var="maxAmountStr" value="${availableCash>1000000.00 ? 1000000.00 : availableCash}" groupingUsed="false" minFractionDigits="2" maxFractionDigits="2"/>
        <input type="number" class="form-control" id="amount" name="amount" placeholder="${availableCash}" step="0.01" min="0.01" max="${availableCash}" required>
        </div>
    </div>
     <h3> </h3>
    <div class="alert alert-info" role="alert">
        The amount you withdraw cannot exceed the available cash.
    </div>
    <h3> </h3>
    <button type="submit" class="btn btn-primary">Submit Request</button>
    <h3>${fn:escapeXml(message)}</h3>
    </form>
  </main>
<%@ include file="footer.jsp" %>
