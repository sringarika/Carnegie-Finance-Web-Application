<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<main>
    <h2>Research Funds</h2>
<div>
</div>
    <h3>Latest Closing Price</h3>
    <table class="table table-bordered table-striped">
      <thead>
         <tr class="table-light-grey">
           <th>Fund Name</th>
           <th>Ticker</th>
           <th>Last Closing Date</th>
           <th class="text-right">Price</th>
         </tr>
      </thead>
      <c:forEach var="researchFund" items="${researchFundList}">
      <tr>
          <td>${fn:escapeXml(researchFund.fundName)}</td>
          <td>${fn:escapeXml(researchFund.ticker)}</td>
          <td>${fn:escapeXml(researchFund.lastClosingDate)}</td>
          <td class="text-right"><fmt:formatNumber value="${researchFund.price}" type="currency"/></td>
      </tr>
      </c:forEach>
    </table>
    <h3>Price History</h3>
    <div class="js-research-fund-chart"></div>
    <c:forEach var="priceHistoryMap" items="${mapList}" varStatus="status">
      <h4>${fn:escapeXml(researchFundList[status.index].fundName)}</h4>
      <table class="table table-bordered table-striped js-fund-table" data-ticker="${fn:escapeXml(researchFundList[status.index].ticker)}">
        <tr>
          <th>Date</th>
          <th class="text-right">Price</th>
        </tr>
        <c:forEach var="date" items="${dateList[status.index]}" varStatus="status2">
          <tr>
            <td class="js-fund-date" data-date-iso="${fn:escapeXml(date)}">
              ${fn:escapeXml(dateDispList[status.index][status2.index])}
            </td>
            <td class="text-right js-fund-price" data-price="${fn:escapeXml(priceHistoryMap.get(date))}">
              <fmt:formatNumber value="${priceHistoryMap.get(date)}" type="currency"/>
            </td>
          </tr>
        </c:forEach>
      </table>
    </c:forEach>
<h3>${fn:escapeXml(message)}</h3>
</main>
<%@ include file="footer.jsp" %>