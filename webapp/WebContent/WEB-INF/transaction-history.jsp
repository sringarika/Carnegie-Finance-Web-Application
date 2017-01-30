<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <c:if test="${isMyAccount}">
      <h2>Transaction History</h2>
    </c:if>
    <c:if test="${not isMyAccount}">
      <h2>Transaction History for ${fn:escapeXml(firstName)} ${fn:escapeXml(lastName)}</h2>
    </c:if>
    <table class="table table-bordered table-striped">
      <thead>
        <tr>
          <th>Transaction Date</th>
          <th>Transaction Type</th>
          <th>Fund</th>
          <th class="text-right">Shares</th>
          <th class="text-right">Price</th>
          <th class="text-right">Total Amount</th>
          <th>Transaction Status</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <c:forEach var="transaction" items="${transactions}">
            <tr>
              <td>${fn:escapeXml(transaction.dateDisp)}</td>
              <td>${fn:escapeXml(transaction.transactionType)}</td>
              <td>${fn:escapeXml(transaction.fundName)}</td>
              <td class="text-right"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${transaction.sharesVal}" /></td>
              <td class="text-right"><fmt:formatNumber value="${transaction.priceVal}" type="currency" /></td>
              <td class="text-right"><fmt:formatNumber value="${transaction.amountVal}" type="currency" /></td>
              <td>${fn:escapeXml(transaction.transactionStatus)}</td>
            </tr>
          </c:forEach>
        <tr>
      </tbody>
    </table>
    <h4>Cash Balance: <fmt:formatNumber value="${cashBalance}" type="currency"/></h4>
    <h4>Available Cash: <fmt:formatNumber value="${availableCash}" type="currency"/></h4>
  </main>
<%@ include file="footer.jsp" %>