<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Create Fund</h2>
    <p>
      <span class="label label-primary">TODO</span>
      Logged-in employees can create new mutual funds. The
employee is prompted for the fund name and its ticker (a short one to five
character identifier which may be used when entering orders or displaying
positions in a concise format). The fund is created immediately, although the first
closing day price for the fund is not provided until the next Transition Day. So
immediately after creating a fund, customers can queue up buy transactions.
    </p>
  </main>
<%@ include file="footer.jsp" %>