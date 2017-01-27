<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
 <main>
    <h2>Deposit Check</h2>
    <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
    <form action="deposit-check.do" method="POST">
    <input type="hidden" name="customerId" value="${customerId}">

    <div class="form-group">
        <label for="amount">Amount to be deposited(in dollars)</label>
        <div class="input-group">
        <div class="input-group-addon">$</div>
        <input type="number" class="form-control" id="amount" name="amount" placeholder="12.34" step="0.01" min="0.01" required>
        </div>
    </div>
     <h3> </h3>
    <div class="alert alert-info" role="alert">
        The transaction will be processed on the end of the trading day.
    </div>
    <h3> </h3>
    <button type="submit" class="btn btn-primary">Deposit Check</button>
    </form>
  </main>
<%@ include file="footer.jsp" %>
