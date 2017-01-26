<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.getSession().setAttribute("userType", "customer"); %>
<%@ include file="header.jsp" %>
  <main>
    <p>${error}</p>
    <h2>Account</h2>
    <table class="table table-striped table-bordered">
    <tr>
      <td>Name</td>
      <td>${firstName} ${lastName}</td>
    </tr>
    <tr>
      <td>Address</td>
      <td>${address}</td>
    </tr>
    <tr>
      <td>Last Trading Day</td>
      <td></td>
    </tr>
    <tr>
      <td>Cash Balance</td>
      <td>${cash}</td>
    </tr>
    </table>

<div>
<h3>Funds</h3>

<%-- <%!
  public class CusFund {
    private int fundId;
    private String fundName;
    private String ticker;
    private double numOfShares;
    private double price;
    private double value;
    
    public CusFund(int fundId, String fundName, String ticker, double numOfShares, double price) {
        this.fundId = fundId;
        this.fundName = fundName;
        this.ticker = ticker;
        this.numOfShares = numOfShares;
        this.price = price;
        this.value = numOfShares * price;
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
    
    public double getNumOfShares() {
        return numOfShares;
    }
    public double getPrice() {
      return price;
    }
    public double getValue() {
      return value;
    }
  }
%>
<%
    CusFund[] cusFunds = {
            new CusFund(1, "Long-Term Treasury", "LTT", 1000.000, 11.88),
            new CusFund(2, "Index Admiral Shares", "IAS", 1000.000, 209.79),
            new CusFund(3, "Strategic Equity", "SE", 5000.000, 32.88),
    };
    request.setAttribute("cusFunds", cusFunds);
%> --%>

  <table class="table table-hoverable">
    <thead>
      <tr class="table-light-grey">
        <th>Fund Name</th>
        <th>Ticker</th>
        <th>Number of shares</th>
        <th>Last Closing Price</th>
        <th>Total Value</th>
      </tr>
    </thead>
    <c:forEach var="position" items="${positions}">
    <tr>
       <td></td>
       <td></td>
       <td>${position.shares}</td>
       <td></td>
       <td></td>
    </tr>
    </c:forEach>
  </table>
  </div>
</main>
<%@ include file="footer.jsp" %>