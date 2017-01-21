<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Sell Funds</h2>
    <form action="account.jsp" method="POST">
      <div class="form-group">
        <label for="fundId">Fund</label>
        <select class="form-control" id="fundId" name="fundId" required>
          <option value="1">EBIZC - ebConsultants Fund</option>
          <option value="2">CMUMC - CMU Math Club Fund</option>
        </select>
      </div>
      <div>
        <label for="shares">Current Shares holding: </label>
      </div>
      <div class="form-group">
        <label for="shares">Shares</label>
        <input type="number" class="form-control" id="shares" name="shares" placeholder="12.345" step="0.001" min="0.001" required>
      </div>
      <div class="alert alert-info" role="alert">
        The transaction will be processed on the end of the trading day. The cash amount depends on the closing price of the fund at that time.
      </div>
      <button type="submit" class="btn btn-primary">Sell</button>
    </form>
  </main>
<%@ include file="footer.jsp" %>