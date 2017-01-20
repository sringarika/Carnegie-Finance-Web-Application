<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Deposit Check</h2>
    <p>
      <span class="label label-primary"></span>
      Name : John Doe
      <br>
      Amount to be deposited: 100$
      <br>
      </p>
     <p>
      <a href="deposit.jsp" class="btn btn-primary" role="button">Deposit Fund</a>
    </p>
  </main>
<%@ include file="footer.jsp" %>