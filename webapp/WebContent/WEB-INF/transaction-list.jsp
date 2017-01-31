<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Transaction List</h2>
    <div class="panel panel-default">
      <div class="panel-body text-center">
        <span>
          <span class="label label-info">${fn:escapeXml(pendingCount)}</span>
          Pending Transaction(s)
        </span>
        <span style="padding-left: 2em;">
          <span class="label label-default">${fn:escapeXml(processedCount)}</span>
          Processed Transaction(s)
        </span>
      </div>
    </div>
    <table class="table table-bordered table-striped">
      <thead>
        <tr>
          <th>Transaction Date</th>
          <th>Customer Username</th>
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
              <td>${fn:escapeXml(transaction.customerName)}</td>
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