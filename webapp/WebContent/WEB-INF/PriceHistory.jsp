<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>
<main>
<h5>Price History</h5>
<table>
<tr>
    <th>Date</th> 
    <th>Price</th>
</tr>
<tr>
 <c:forEach items="${mapList}" var="map">
<tr>
    <th>Date</th>
    <th>Price</th>
</tr>
           <c:forEach items="${priceHistoryMap}" var="entry">
                      ${fn:escapeXml(entry.key)}<br>
                      ${fn:escapeXml(entry.value)}<br>
           </c:forEach>
<br><br>
 </c:forEach>
</tr>
</table>
</main>