<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Customer List</h2>
    
    <table class="table table-bordered table-striped">
      <tr>
        <th>Username</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Action</th>
      </tr>
      <c:forEach var="customer" items="${customers}">
        <tr>
          <td>${fn:escapeXml(customer.username)}</td>
          <td>${fn:escapeXml(customer.firstname)}</td>
          <td>${fn:escapeXml(customer.lastname)}</td>
          <td>
            <a class="btn btn-primary" href="view-customer.do?customerId=${fn:escapeXml(customer.customerId)}">View Details</a>
            <a class="btn btn-info" href="transaction-history.do?customerId=${fn:escapeXml(customer.customerId)}">Transaction History</a>
            <a class="btn btn-warning" href="reset-password.do?customerId=${fn:escapeXml(customer.customerId)}">Reset Password</a>
            <a class="btn btn-success" href="deposit-check.do?customerId=${fn:escapeXml(customer.customerId)}">Deposit Check</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </main>
<%@ include file="footer.jsp" %>