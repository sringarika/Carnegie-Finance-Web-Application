<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Transition Day</h2>
    <c:if test="${pendingTransactionCount > 0}">
      <p class="alert alert-info">
        There are ${fn:escapeXml(pendingTransactionCount)} transaction(s) pending.
        <a class="alert-link" href="transaction-list.do">Show details...</a>
      </p>
    </c:if>
    <c:if test="${pendingTransactionCount == 0}">
      <p class="alert alert-info">
        There are no pending transactions.
        <a class="alert-link" href="transaction-list.do">Show transaction list...</a>
      </p>
    </c:if>
    <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
    <form action="transition-day.do" method="POST" class="form-horizontal">
      <div class="form-group">
        <label class="col-sm-3 control-label">Last Closing Date:</label>
        <div class="col-sm-9">
          <p class="form-control-static">${fn:escapeXml(lastClosingDateDisp)}</p>
          <input type="hidden" name="lastClosingDateISO" value="${fn:escapeXml(lastClosingDateISO)}">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label" for="password">Current Closing Date: </label>
        <div class="col-sm-9">
          <input type="date" class="form-control" name="closingDateISO" value="${minClosingDateISO}" min="${minClosingDateISO}" data-format="MM/dd/yyyy" required>
        </div>
      </div>
      <table class="table table-striped table-bordered">
        <tr>
          <th>Fund ID</th>
          <th>Fund Name</th>
          <th>Ticker</th>
          <th class="text-right">Last Closing Price</th>
          <th>New Closing Price</th>
        </tr>
        <c:forEach var="fund" items="${funds}">
          <tr>
            <td>${fn:escapeXml(fund.fundId)}<input type="hidden" name="fundIds" value="${fn:escapeXml(fund.fundId)}"></td>
            <td>${fn:escapeXml(fund.name)}</td>
            <td>${fn:escapeXml(fund.ticker)}</td>
            <td class="text-right">
              <fmt:formatNumber var="formattedPrice" type="currency" value="${lastPriceForFund[fund.fundId]}" />
              <c:out value="${formattedPrice}" default="N/A"/>
            </td>
            <td>
              <input type="number" class="form-control" name="closingPrices" step="0.01" min="10.00" max="1000.00" required>
            </td>
          </tr>
        </c:forEach>
      </table>
      <button type="submit" class="btn btn-primary">Transition Day</button>
    </form>
  </main>
<%@ include file="footer.jsp" %>