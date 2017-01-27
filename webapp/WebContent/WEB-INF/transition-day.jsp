<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Transition Day</h2>
    <p class="alert alert-info">
      There are <a href="transaction-list.jsp">42 transactions</a> pending.
      <a href="transaction-list.jsp">Show details...</a>
    </p>
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
          <input type="date" class="form-control" name="closingDateISO" value="${minClosingDateISO}" min="${minClosingDateISO}" data-format="MM/dd/yyyy" required>
        </div>
      </div>
      <table class="table table-striped table-bordered">
        <tr>
          <th>Fund ID</th>
          <th>Fund Name</th>
          <th>Ticker</th>
          <th>Last Closing Price</th>
        </tr>
        <c:forEach var="fund" items="${funds}">
          <tr>
            <td>${fn:escapeXml(fund.fundId)}<input type="hidden" name="fundIds" value="${fn:escapeXml(fund.fundId)}"></td>
            <td>${fn:escapeXml(fund.name)}</td>
            <td>${fn:escapeXml(fund.ticker)}</td>
            <td><input type="number" class="form-control" name="closingPrices" placeholder="10000.00" step="0.01" min="1.00" max="10000.00" required></td>
          </tr>
        </c:forEach>
      </table>
      <button type="submit" class="btn btn-primary">Transition Day</button>
    </form>
  </main>
<%@ include file="footer.jsp" %>