<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Transition Day</h2>
    <p>
      <span class="label label-primary">TODO</span>
      To simulate trading days, a logged-in employee “transitions
the system” to end each trading day. The employee enters the date of the trading
day that has just ended (greater than the date of previously ended trading day).
The employee also provides the closing prices for each fund for this trading day.
Pending transactions are then executed in the order in which they were queued
using the new closing fund prices and this trading date. The database is updated
accordingly. (The database should store a null execution date to indicate that a
transaction is pending. When a pending transaction is processed, the transaction
date is changed to this trading day’s date and the customer’s cash balance and
share balance in the effected fund are updated accordingly.) 
    </p>
  </main>
<%@ include file="footer.jsp" %>