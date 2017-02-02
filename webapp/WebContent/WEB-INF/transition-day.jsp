<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Transition Day</h2>
    <c:if test="${pendingTransactionCount > 0}">
      <p class="alert alert-success">
        There are ${fn:escapeXml(pendingTransactionCount)} transaction(s) pending.
        <a class="alert-link" href="transaction-list.do">Show details...</a>
      </p>
    </c:if>
    <c:if test="${pendingTransactionCount == 0}">
      <p class="alert alert-info">
        There are no pending transactions.
        <a class="alert-link" href="transaction-list.do">Show transaction list...</a>
      </p>
    </c:if>
    <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
    <form action="transition-day.do" method="POST" class="form-horizontal">
      <div class="form-group">
        <label class="col-sm-3 control-label">Last Closing Date:</label>
        <div class="col-sm-9">
          <p class="form-control-static">${fn:escapeXml(lastClosingDateDisp)}</p>
          <input type="hidden" name="lastClosingDateISO" value="${fn:escapeXml(lastClosingDateISO)}">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label" for="password">Current Closing Date: </label>
        <div class="col-sm-9">
          <input type="date" class="form-control" name="closingDateISO" value="${minClosingDateISO}" min="${minClosingDateISO}" data-format="MM/dd/yyyy" data-polyfill="all" required>
        </div>
      </div>
      <div class="alert alert-info" role="alert">
        Note: Prices must be between <b>$10.00 ~ $1,000.00</b>! Also, price for each fund cannot be incremented by more than <b>100%</b> or decremented by more than <b>50%</b>!
      </div>
      <table class="table table-striped table-bordered">
        <tr>
          <th>Fund Name</th>
          <th>Ticker</th>
          <th class="text-right">Last Closing Price</th>
          <th>New Closing Price</th>
        </tr>
        <c:forEach var="fund" items="${funds}">
          <tr>
            <td>${fn:escapeXml(fund.name)}</td>
            <td>${fn:escapeXml(fund.ticker)}</td>
            <td class="text-right" data-last-price="${fn:escapeXml(lastPriceForFund[fund.fundId])}">
              <fmt:formatNumber var="formattedPrice" type="currency" value="${lastPriceForFund[fund.fundId]}" />
              <c:out value="${formattedPrice}" default="N/A"/>
            </td>
            <td>
              <input type="hidden" name="fundIds" value="${fn:escapeXml(fund.fundId)}">
              <div class="input-group">
                <div class="input-group-addon">$</div>
                <fmt:formatNumber var="minAmountStr" value="${minPriceForFund[fund.fundId]}" groupingUsed="false" minFractionDigits="2" maxFractionDigits="2"/>
                <fmt:formatNumber var="minAmountDisp" value="${minPriceForFund[fund.fundId]}" type="currency"/>
                <fmt:formatNumber var="maxAmountStr" value="${maxPriceForFund[fund.fundId]}" groupingUsed="false" minFractionDigits="2" maxFractionDigits="2"/>
                <fmt:formatNumber var="maxAmountDisp" value="${maxPriceForFund[fund.fundId]}" type="currency"/>
                <input type="number" class="form-control" name="closingPrices" step="0.01" min="${fn:escapeXml(minAmountStr)}" max="${fn:escapeXml(maxAmountStr)}"
                  placeholder="${fn:escapeXml(minAmountDisp)} ~ ${fn:escapeXml(maxAmountDisp)}" required>
              </div>
            </td>
          </tr>
        </c:forEach>
      </table>
      <p class="alert alert-danger js-price-alert" style="display: none;"></p>
      <button type="submit" class="btn btn-primary js-transition-submit">Transition Day</button>
    </form>
  </main>
<%@ include file="footer.jsp" %>