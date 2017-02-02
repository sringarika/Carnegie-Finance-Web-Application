<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

  <main>
    <h2>Create Fund</h2>
    
    <br>
    <h4>List of Available Funds</h4>
    
    <table class="table table-bordered table-striped">
      <tr>
        <th>Fund Name</th>
        <th>Ticker</th>
      </tr>

      <c:forEach var="fund" items="${fundList}">
        <tr>
          <td>${fn:escapeXml(fund.name)}</td>
          <td>${fn:escapeXml(fund.ticker)}</td>
        </tr>
      </c:forEach>
    </table>
    
    
    <form action="create-fund.do" method="POST">
      <div class="form-group">
        <label for="username">Fund Name</label>
        <input type="text" class="form-control" id="fund" name = "fund" 
        placeholder="Fund name can only contain letters, numbers and spaces, up to 20 characters" maxlength="20" required>
      </div>
      <div class="form-group">
        <label for="username">Ticker</label>
        <input type="text" class="form-control" id="ticker" name = "ticker"
        placeholder="Ticker can only contain letters, up to 5 characters" maxlength="5" required>
      </div>
      <br>
      <c:if test="${(!empty error)}">
        <div class="alert alert-danger">
          ${fn:escapeXml(error)}
        </div>
      </c:if>
      <div class="alert alert-info" role="alert">
        Note: Fund Name and Ticker must be unique.
      </div>
      <br>
      <button type="submit" class="btn btn-primary">Create Fund</button>
    </form>
  </main>
<%@ include file="footer.jsp" %>