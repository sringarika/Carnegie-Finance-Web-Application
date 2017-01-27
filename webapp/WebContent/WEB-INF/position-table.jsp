<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <table class="table table-hoverable">
      <thead>
        <tr class="table-light-grey">
          <c:if test="${not empty param.radioInput}">
            <th></th>
          </c:if>
          <th>Fund Name</th>
          <th>Ticker</th>
          <th class="text-right">
            <c:if test="${empty param.radioInput}">
              Number of shares
            </c:if>
            <c:if test="${not empty param.radioInput}">
              Available shares
            </c:if>
            </th>
          <th class="text-right">Last Closing Price</th>
          <th class="text-right">Total Value</th>
        </tr>
      </thead>
      <c:forEach var="position" items="${positions}">
        <tr>
          <c:if test="${not empty param.radioInput}">
            <td><input type="radio" name="${param.radioInput}" value="${fn:escapeXml(position.fundId)}" required></td>
          </c:if>
          <td>${fn:escapeXml(position.fundName)}</td>
          <td>${fn:escapeXml(position.ticker)}</td>
          <td class="text-right"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${position.shares}" /></td>
          <c:if test="${position.priceAvailable}">
            <td class="text-right"><fmt:formatNumber value="${position.price}" type="currency"/></td>
            <td class="text-right"><fmt:formatNumber value="${position.value}" type="currency"/></td>
          </c:if>
          <c:if test="${not position.priceAvailable}">
            <td colspan="2" class="warning text-center">Not Available</td>
          </c:if>
        </tr>
      </c:forEach>
    </table>