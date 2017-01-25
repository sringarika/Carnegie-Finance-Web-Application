<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Transaction History for ${firstName} ${lastName}</h2>
    <p style="font-size: 12pt; font-weight: bold; color: red"> ${error} </p>
    <table class="table table-bordered table-striped">
  	  <thead>
	  <tr>
	    <th>ID</th>
	    <th>Transaction Date</th>
	    <th>Transaction Type</th>
	    <th>Fund</th>
	    <th>Shares</th>
	    <th>Price</th>
	    <th>Total Amount</th>
	    <th>Transaction Status</th>
	  </tr>
	  </thead>
	  <tbody>
	  <tr>
	    <c:forEach var="transaction" items="${transactions}">
	      <th>"${transaction.transactionId}"</th>
	      <th>"${transaction.executeDate}"</th>
	      <th>"${transaction.type}"</th>
	      <th>"${transaction.fundName}"</th>
	      <th>"${transaction.shares}"</th>
	      <th>"${transaction.price}"</th>
	      <th>"${transaction.amount}"</th>
	      <th>"${transaction.status}"</th>
	    </c:forEach>
	  <tr>
	  </tbody>
	</table>
  </main>
<%@ include file="footer.jsp" %>