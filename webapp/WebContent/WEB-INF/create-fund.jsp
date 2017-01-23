<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<%!
  public class Fund {
    private String fundName;
    private String ticker;
    
    public Fund(String fundName, String ticker) {
        this.fundName = fundName;
        this.ticker = ticker;
    }
    
    public String getFundName() {
        return fundName;
    }
    
    public String getTicker() {
        return ticker;
    }
    
  }
%>
<%
  Fund[] fund = {
          new Fund("Google", "Googl"),
          new Fund("Apple", "ijobs"),
          new Fund("Microsoft", "gates"),
          };
  request.setAttribute("fund", fund);
%>
  <main>
    <h2>Create Fund</h2>
    
    <br>
    <h4>List of available funds</h4>
    
    <table class="table table-bordered table-striped">
      <tr>
        <th>Fund Name</th>
        <th>Ticker</th>
      </tr>
      <c:forEach var="fund" items="${fund}">
        <tr>
          <td>${fn:escapeXml(fund.fundName)}</td>
          <td>${fn:escapeXml(fund.ticker)}</td>
        </tr>
      </c:forEach>
    </table>
    
    
	<form action="create-fund.jsp" method="POST">
      <div class="form-group">
        <label for="username">Fund Name</label>
        <input type="text" class="form-control" id="fundname" placeholder="">
      </div>
      <div class="form-group">
        <label for="username">Ticker</label>
        <input type="text" class="form-control" id="ticker" placeholder="">
      </div>
      </p>
        <div class="alert alert-info" role="alert">
        The first closing day price for the fund is not provided until the next Transition Day.
      </div>
     <p>
      <a href="confirmfund.jsp" class="btn btn-primary" role="button">Create Fund</a>
    </p>

  </main>
<%@ include file="footer.jsp" %>