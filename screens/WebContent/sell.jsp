<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Sell Funds</h2>
    <p>
      <span class="label label-primary">TODO</span>
Logged-in customers can sell shares of a mutual fund they own. The
customer specifies the fund and the number of shares to sell. You must check to
ensure that this transaction (plus other pending sell orders) will not cause the
share balance to go negative for the customer’s position in this fund. If there are
no errors, the sell order is queued as a pending transaction. Proceeds from the
sale go into the selling customer's cash balance when the transaction is processed. 
    </p>
  </main>
<%@ include file="footer.jsp" %>