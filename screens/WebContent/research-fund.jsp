<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Research Funds</h2>
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
    <table class="table table-striped">
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