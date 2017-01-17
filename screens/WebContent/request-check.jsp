<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Request Check</h2>
    <p>
      <span class="label label-primary">TODO</span>
Logged-in customers can withdraw money from their cash
balances by requesting a check. You must check to ensure that this transaction
(plus other pending check requests and buy orders) will not cause the cash
balance to go negative. If there are no errors, the check request is queued as a
pending transaction
    </p>
  </main>
<%@ include file="footer.jsp" %>