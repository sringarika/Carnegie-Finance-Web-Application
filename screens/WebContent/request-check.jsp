<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Request Check</h2>

    <table class="table">
    <thead>
      <tr class="table-light-grey">
        <th>Available Cash: $666,000.00</th>
      </tr>
    </thead>
    </table>
    <div class="form-group">
        <label for="amount">Amount to withdraw (in dollars)</label>
        <div class="input-group">
        <div class="input-group-addon">$</div>
        <input type="number" class="form-control" id="amount" name="amount" placeholder="12.34" step="0.01" min="0.01" required>
    </div>
     <h3> </h3>
    <div class="alert alert-info" role="alert">
        The amount you withdraw cannot exceed the available cash.
    </div>
    <h3> </h3>
    <button type="submit" class="btn btn-primary">Submit Request</button>
  </main>
<%@ include file="footer.jsp" %>