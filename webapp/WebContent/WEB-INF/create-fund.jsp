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
        <input type="text" class="form-control" id="fund" name = "fund" placeholder="">
      </div>
      <div class="form-group">
        <label for="username">Ticker</label>
        <input type="text" class="form-control" id="ticker" name = "ticker" placeholder="">
      </div>
      <br>
      	     <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
    <br>
        <div class="alert alert-info" role="alert">
        Disclaimer:The first closing day price for the fund is not provided until the next Transition Day.
      </div>
     <p>
     <br>
           <button type="submit" class="btn btn-primary">Create Fund</button>
     
    </p>
</form>
  </main>
<%@ include file="footer.jsp" %>