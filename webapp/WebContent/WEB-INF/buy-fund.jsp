<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Buy Funds</h2>
    <form action="buy-fund.do" method="POST">
      <div class="form-group">
        <label for="fundId">Fund</label>
        <select class="form-control" id="fundId" name="fundId" required>
          <option value="1">EBIZC - ebConsultants Fund</option>
          <option value="2">CMUMC - CMU Math Club Fund</option>
        </select>
      </div>
      <div class="form-group">
        <label for="amount">Amount (in dollars)</label>
        <div class="input-group">
          <div class="input-group-addon">$</div>
          <input type="number" class="form-control" id="amount" name="amount" placeholder="12.34" step="0.01" min="0.01" required>
        </div>
      </div>
      <div class="alert alert-info" role="alert">
        The transaction will be processed on the end of the trading day. The number of shares purchased depends on the closing price of the fund at that time.
      </div>
      <button type="submit" class="btn btn-primary">Buy</button>
    </form>
  </main>
<%@ include file="footer.jsp" %>