<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Buy Funds</h2>
    <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
    <c:if test="${availableCash < 10}">
      <div class="alert alert-info">
        You don't have enough available cash to buy funds.
      </div>
    </c:if>
    <c:if test="${availableCash >= 10}">
      <form action="buy-fund.do" method="POST">
        <div class="form-group">
          <label for="fundId">Select Fund</label>
          <select class="form-control" id="fundId" name="fundId" required>
            <c:forEach var="fund" items="${funds}">
              <option class="js-fund-option" value="${fn:escapeXml(fund.fundId)}">${fn:escapeXml(fund.name)}</option>
            </c:forEach>
          </select>
        </div>
        <div class="form-group">
          <label for="amount">Amount (in dollars)</label>
          <div class="input-group">
            <div class="input-group-addon">$</div>
            <fmt:formatNumber var="maxAmountStr" value="${availableCash>1000000.00 ? 1000000.00 : availableCash}" groupingUsed="false" minFractionDigits="2" maxFractionDigits="2"/>
            <fmt:formatNumber var="maxAmountDisp" value="${availableCash>1000000.00 ? 1000000.00 : availableCash}" type="currency"/>
            <input type="number" class="form-control" id="amount" name="amount" placeholder="$10.00 ~ ${maxAmountDisp}" step="0.01" min="10.00" max="${maxAmountStr}" required>
          </div>
        </div>
        <div>
          <label for="cash">Available Cash: <fmt:formatNumber value="${availableCash}" type="currency"/></label>
        </div>
        <div class="alert alert-info" role="alert">
          The transaction will be processed on the end of the trading day. The number of shares purchased depends on the closing price of the fund at that time.
        </div>
        <button type="submit" class="btn btn-primary">Buy</button>
      </form>
    </c:if>
  </main>
  <script>
    document.addEventListener('DOMContentLoaded', function () {
      var defaultOption = null;
      if (location.search.indexOf("?fundId=") === 0) {
        var defaultFundId = parseInt(location.search.split(/[=&]/)[1]);
        if (!isNaN(defaultFundId)) defaultOption = document.querySelector(
            '.js-fund-option[value="' + defaultFundId + '"]');
      }
      if (!defaultOption) {
        defaultOption = document.querySelector('.js-fund-option');
      }
      if (!defaultOption) return;
      var fundIdSelect = document.getElementById('fundId');
      if (!fundIdSelect) return;
      fundIdSelect.value = defaultOption.getAttribute('value');
    });
  </script>
<%@ include file="footer.jsp" %>