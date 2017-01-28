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
        <jsp:include page="position-table.jsp">
          <jsp:param name="radioInput" value="fundId"/>
        </jsp:include>
      </div>
      <div class="form-group">
        <label for="shares">Shares to sell</label>
        <input type="number" class="form-control" id="shares" name="shares" placeholder="1.000" step="0.001" min="0.001" max="1000000.000" required>
        
        <%-- The following datalist is for JavaScript validation. --%>
        <datalist id="fund-ids" style="display: none;">
          <c:forEach var="position" items="${positions}">
            <fmt:formatNumber var="availSharesStr" value="${position.shares}" groupingUsed="false" minFractionDigits="3" maxFractionDigits="3"/>
            <option value="${fn:escapeXml(position.fundId)}" data-avail-shares="${fn:escapeXml(availSharesStr)}"></option>
          </c:forEach>
        </datalist>
      </div>
      <div class="alert alert-info" role="alert">
        The transaction will be processed on the end of the trading day. The cash amount depends on the closing price of the fund at that time.
      </div>
      <button type="submit" class="btn btn-primary">Sell</button>
    </form>
  </main>
  <script>
    (function() {
      var availSharesForFund;
      var updateMaxShares = function() {
        if (!availSharesForFund) {
          availSharesForFund = {};
          document.querySelectorAll('#fund-ids option').forEach(function(option) {
            var fundId = option.getAttribute('value');
            var availShares = option.getAttribute('data-avail-shares');
            availSharesForFund[fundId] = availShares;
          });
          console.log(availSharesForFund);
        }
        var checkedInput = document.querySelector('input[name="fundId"]:checked');
        if (!checkedInput) {
          checkedInput = document.querySelector('input[name="fundId"]');
          if (!checkedInput) return;
          checkedInput.checked = true;
        }
        var fundId = checkedInput.value;
        var availShares = availSharesForFund[fundId];
        if (typeof availShares === 'undefined') return;
        var sharesInput = document.getElementById('shares');
        if (!sharesInput) return;
        sharesInput.max = availShares;
        sharesInput.placeholder = availShares;
      };
      document.addEventListener('change', function(e) {
        var element = e.target;
        if (element.getAttribute('name') === 'fundId') {
          updateMaxShares();
        }
      });
      document.addEventListener('DOMContentLoaded', updateMaxShares);
    })();

  </script>
<%@ include file="footer.jsp" %>