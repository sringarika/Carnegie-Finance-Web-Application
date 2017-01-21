<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <img src="check-mark.png" alt="create customer page" style="width:15%;height:15%;">
    <h3>${fn:escapeXml(message)}</h3>
  </main>
<%@ include file="footer.jsp" %>