<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>
  <main>
    <c:if test="${isMyAccount}">
      <h2>My Account</h2>
    </c:if>
    <c:if test="${not isMyAccount}">
      <h2>View Customer Account: ${showCustomer.username}</h2>
    </c:if>
    <table class="table table-striped table-bordered">
    <tr>
      <td>Name</td>
      <td>${showCustomer.firstname} ${showCustomer.lastname}</td>
    </tr>
    <tr>
      <td>Address</td>
      <td>
        ${showCustomer.addrLine1}
        <br>
        ${showCustomer.addrLine2}
        <br>
        ${showCustomer.city}, ${showCustomer.state} ${showCustomer.zip}
      </td>
    </tr>
    <tr>
      <td>Last Trading Day</td>
      <td>${lastTradingDay}</td>
    </tr>
    <tr>
      <td>Cash Balance</td>
      <td>${showCustomer.cash}</td>
    </tr>
    </table>
    
    <jsp:include page="position-table.jsp"></jsp:include>
    
  </main>
<%@ include file="footer.jsp" %>