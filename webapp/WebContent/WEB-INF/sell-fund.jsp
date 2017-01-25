<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Sell Funds</h2>
    <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
    <form action="sell-fund.do" method="POST">
      <div class="form-group">
        <label for="fundId">Please select fund to sell</label>
      <table class="table table-hoverable">
          <thead>
            <tr class="table-light-grey">
              <th></th>
              <th>Fund Name</th>
              <th>Ticker</th>
              <th class="text-right">Number of shares</th>
              <th class="text-right">Last Closing Price</th>
              <th class="text-right">Total Value</th>
            </tr>
          </thead>
          <c:forEach var="cusFund" items="${cusFunds}">
          <tr>
                <td><input type="radio" name="fundId" value="${fn:escapeXml(cusFund.fundId)}" required></td>
                <td>${fn:escapeXml(cusFund.fundName)}</td>
                <td>${fn:escapeXml(cusFund.symbol)}</td>
                <td class="text-right"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${cusFund.numOfShares}" /></td>
                <td class="text-right"><fmt:formatNumber value="${cusFund.price}" type="currency"/></td>
                <td class="text-right"><fmt:formatNumber value="${cusFund.value}" type="currency"/></td>
          </tr>
          </c:forEach>
        </table>
      </div>
      <div class="form-group">
        <label for="shares">Shares to sell</label>
        <input type="number" class="form-control" id="shares" name="shares" placeholder="12.345" step="0.001" min="1.000" max="1000000.000" required>
        <%-- TODO(yuchen): Write some JS to update the max attr above so it can also be validated on client-side. --%>
      </div>
      <div class="alert alert-info" role="alert">
        The transaction will be processed on the end of the trading day. The cash amount depends on the closing price of the fund at that time.
      </div>
      <button type="submit" class="btn btn-primary">Sell</button>
    </form>
  </main>
<%@ include file="footer.jsp" %>