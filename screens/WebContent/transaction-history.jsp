<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Transaction History</h2>
    <p>
      <span class="label label-primary">TODO</span>
Logged-in customers can see a history of their
transactions. The history should show the transaction date, the operation (buy,
sell, request check, deposit), the fund name, the number of shares, the share price,
and the dollar amount. For cash operations (deposit and check requests) the
number of shares and share price columns are left blank or not provided. All
pending transactions (to buy, sell, check request, deposit) must also appear in the
transaction history as pending with the appropriate columns left blank or marked
    </p>

    <p>
      <span class="label label-primary">TODO</span>
      Logged-in employees can view a
customer's transaction history, as described above.
    </p>
  </main>
<%@ include file="footer.jsp" %>