<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Transaction History for ${fn:escapeXml(firstName)} ${fn:escapeXml(lastName)}</h2>
    <p style="font-size: 12pt; font-weight: bold; color: red"> ${error} </p>
    <table class="table table-bordered table-striped">
      <thead>
        <tr>
          <th>ID</th>
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
              <td>${fn:escapeXml(transaction.transactionId)}</td>
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
  </main>
<%@ include file="footer.jsp" %>