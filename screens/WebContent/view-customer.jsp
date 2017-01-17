<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>View Customer Account</h2>
    <p>
      <span class="label label-primary">TODO</span>
      Logged-in employees can view a customer's account
information, including a customer's name, address, date of the last trading day,
cash balance, number of shares of each fund owned and the value of that position. 
    </p>
    <p>
      <a href="transaction-history.jsp" class="btn btn-primary" role="button">View Transaction History</a>
      <a href="reset-password.jsp" class="btn btn-default" role="button">Reset Password</a>
    </p>
  </main>
<%@ include file="footer.jsp" %>