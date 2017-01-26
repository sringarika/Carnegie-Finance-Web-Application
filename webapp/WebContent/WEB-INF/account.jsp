<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <p>${error}</p>
    <h2>Account</h2>
    <table class="table table-striped table-bordered">
    <tr>
      <td>Name</td>
      <td>${firstName} ${lastName}</td>
    </tr>
    <tr>
      <td>Address</td>
      <td>${address}</td>
    </tr>
    <tr>
      <td>Last Trading Day</td>
      <td></td>
    </tr>
    <tr>
      <td>Cash Balance</td>
      <td>${cash}</td>
    </tr>
    </table>

<div>
<h3>Funds</h3>
  <table class="table table-hoverable">
    <thead>
      <tr class="table-light-grey">
        <th>Fund Name</th>
        <th>Ticker</th>
        <th>Number of shares</th>
        <th>Last Closing Price</th>
        <th>Total Value</th>
      </tr>
    </thead>
    <c:forEach var="position" items="${positions}">
    <tr>
       <td></td>
       <td></td>
       <td>${position.shares}</td>
       <td></td>
       <td></td>
    </tr>
    </c:forEach>
  </table>
  </div>
</main>
<%@ include file="footer.jsp" %>