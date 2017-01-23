<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h3>Error</h3>
    <div class="alert alert-danger">
      ${fn:escapeXml(error)}
    </div>
  </main>
<%@ include file="footer.jsp" %>