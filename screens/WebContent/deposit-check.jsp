<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Deposit Check</h2>
    <br>
    <p>
      <span class="label label-primary"></span>
      <b>First name:</b> <input type="text" name="FirstName" value="John Doe"><br>
      <br>
      <div class="form-group">
        <label for="amount">Amount to be deposited(in dollars)</label>
        <div class="input-group">
          <div class="input-group-addon">$</div>
          <input type="number" class="form-control" id="amount" name="amount" placeholder="1.00" step="0.01" min="0.01" required>
        </div>
      <br>
      </p>
      <div class="alert alert-info" role="alert">
        The transaction will be processed on the end of the trading day.
      </div>
     <p>
      <a href="deposit.jsp" class="btn btn-primary" role="button">Deposit Fund</a>
    </p>
    
  </main>
<%@ include file="footer.jsp" %>