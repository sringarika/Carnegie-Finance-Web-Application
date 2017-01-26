<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Transaction List</h2>
    <p style="font-size: 12pt; font-weight: bold; color: red"> ${error} </p>
    <table class="table table-bordered table-striped">
  	  <thead>
	  <tr>
	    <th>ID</th>
	    <th>Transaction Date</th>
	    <th>Transaction Type</th>
	    <th>Fund</th>
	    <th>Shares</th>
	    <th>Price</th>
	    <th>Total Amount</th>
	    <th>Transaction Status</th>
	  </tr>
	  </thead>
	  <tbody>
	  <tr>
	  <tr>
	  </tbody>
	</table>
  </main>
<%@ include file="footer.jsp" %>