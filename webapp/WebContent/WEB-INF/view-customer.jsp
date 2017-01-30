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
      <h2>View Customer Account: ${fn:escapeXml(showCustomer.firstname)} ${fn:escapeXml(showCustomer.lastname)}</h2>
    </c:if>
    <table class="table table-striped table-bordered">
    <tr>
      <td>Name</td>
      <td>${fn:escapeXml(showCustomer.firstname)} ${fn:escapeXml(showCustomer.lastname)}</td>
    </tr>
    <tr>
      <td>Address</td>
      <td>
        ${fn:escapeXml(showCustomer.addrLine1)}
        <br>
        ${fn:escapeXml(showCustomer.addrLine2)}
        <br>
        ${fn:escapeXml(showCustomer.city)}, ${fn:escapeXml(showCustomer.state)} ${fn:escapeXml(showCustomer.zip)}
      </td>
    </tr>
    <tr>
      <td>Last Trading Date</td>
      <td>${fn:escapeXml(lastTradingDateDisp)}</td>
    </tr>
    <tr>
      <td>Cash Balance</td>
      <td class="text-right"><fmt:formatNumber value="${showCustomer.cash}" type="currency"/></td>
    </tr>
    <tr>
      <td>Available Cash</td>
      <td class="text-right"><fmt:formatNumber value="${availableCash}" type="currency"/></td>
    </tr>
    </table>
    
    <c:if test="${(empty positions) && isMyAccount}">
      <div class="alert alert-info">
        You don't have position in any fund right now.
        Do you want to <a class="alert-link" href="buy-fund.do">buy some funds</a>?
      </div>
    </c:if>
    <c:if test="${(empty positions) && !isMyAccount}">
      <div class="alert alert-info">
        This customer does not have any position.
      </div>
    </c:if>
    <c:if test="${(!empty positions)}">
      <jsp:include page="position-table.jsp"></jsp:include>
    </c:if>
    
  </main>
<%@ include file="footer.jsp" %>