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
     <h3>Current Funds</h3>
<%!
  public class Fund {
    private int fundId;
    private String fundName;
    private String ticker;
    private double price;
    private double returnRate;
    private String annualReturn;
    
    public Fund(int fundId, String fundName, String ticker, double price, double returnRate) {
        this.fundId = fundId;
        this.fundName = fundName;
        this.ticker = ticker;
        this.price = price;
        this.returnRate = returnRate;
        this.annualReturn = String.valueOf(returnRate) + "%";
    }
    
    public int getFundId() {
        return fundId;
    }
    
    public String getFundName() {
        return fundName;
    }
    
    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return price;
    }
    public String getAnnualReturn() {
        return annualReturn;
    }
  }
%>
<%
    Fund[] funds = {
         new Fund(1, "ebConsultants Fund", "EBIZC", 66.66, 10.00),
         new Fund(2, "CMU Math Club Fund", "CMUMC", 23.33, 16.54),
         new Fund(3, "Long-Term Investment Grade", "LTIG", 10.13, 7.82),
         new Fund(4, "International Value", "IV", 32.60, 4.46),
         new Fund(5, "Pennslyvania Long-Term Tax Exempt", "PLTTE", 11.43, 0.66),
         new Fund(6, "High Yield Corporate", "HYC", 5.87, 11.19),
    };
    request.setAttribute("funds", funds);
%>
<div>
</div>
    <table class="table table-bordered table-striped">
      <thread>
         <tr class="table-light-grey">
         <th>ID</th>
         <th>Fund Name</th>
         <th>Ticker</th>
         <th>Price</th>
         <th>Annual Return</th> 
         </tr>
      </thread>
      <c:forEach var="fund" items="${funds}">
      <tr>
          <td>${fn:escapeXml(fund.fundId)}</td>
          <td>${fn:escapeXml(fund.fundName)}</td>
          <td>${fn:escapeXml(fund.ticker)}</td>
          <td>${fn:escapeXml(fund.price)}</td>
          <td>${fn:escapeXml(fund.annualReturn)}</td>
      </tr>
      </c:forEach>
    </table>
  </main>
<%@ include file="footer.jsp" %>