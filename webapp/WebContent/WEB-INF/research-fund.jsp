<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Research Funds</h2>
<div>
</div>
    <table class="table table-bordered table-striped">
      <thread>
         <tr class="table-light-grey">
         <th>ID</th>
         <th>Fund Name</th>
         <th>Ticker</th>
         <th>Last Closing Date</th>
         <th>Price</th>
         </tr>
      </thread>
      <c:forEach var="fund" items="${fundList}">
      <tr>
      	  <td>${fn:escapeXml(fund.fundId)}</td>
          <td>${fn:escapeXml(fund.fundName)}</td>
          <td>${fn:escapeXml(fund.ticker)}</td>
          <td>${fn:escapeXml(fund.lastClosingDate)}</td>
          <td>${fn:escapeXml(fund.price)}</td>
      </tr>
      </c:forEach>
    </table>
  </main>
<%@ include file="footer.jsp" %>