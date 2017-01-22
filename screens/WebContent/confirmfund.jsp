<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.getSession().setAttribute("userType", "employee"); %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Success!</h2>
    <p>
      The fund has been successfully created!
    </p>
  </main>
<%@ include file="footer.jsp" %>