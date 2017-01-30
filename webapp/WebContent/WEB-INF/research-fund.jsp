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
      <c:forEach var="researchFund" items="${researchFundList}">
      <tr>
      	  <td>${fn:escapeXml(researchFund.fundId)}</td>
          <td>${fn:escapeXml(researchFund.fundName)}</td>
          <td>${fn:escapeXml(researchFund.ticker)}</td>
          <td>${fn:escapeXml(researchFund.lastClosingDate)}</td>
          <td>${fn:escapeXml(researchFund.price)}</td>
      </tr>
      </c:forEach>
    </table>

<h3>${fn:escapeXml(message)}</h3>
</main>
<%@ include file="footer.jsp" %>