<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.getSession().setAttribute("userType", "customer"); %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Account</h2>
    <p>
      <span class="label label-primary">TODO</span>
      After successfully logging in, a customer sees his account,
  including his name, address, date of the last trading day, cash balance, number of
  shares of each fund owned and the value of that position (shares times price of
  fund as of the last trading day). From this view there will be links to most other
  operations. 
    </p>
  </main>
<%@ include file="footer.jsp" %>