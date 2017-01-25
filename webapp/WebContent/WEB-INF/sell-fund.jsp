<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>
<%!
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
%>
  <main>
    <h2>Sell Funds</h2>
    <form action="sell-fund.do" method="POST">
      <div class="form-group">
        <label for="fundId">Please select fund to sell</label>
      <table class="table table-hoverable">
          <thead>
            <tr class="table-light-grey">
              <th></th>
              <th>Fund Name</th>
              <th>Ticker</th>
              <th class="text-right">Number of shares</th>
              <th class="text-right">Last Closing Price</th>
              <th class="text-right">Total Value</th>
            </tr>
          </thead>
          <c:forEach var="cusFund" items="${cusFunds}">
          <tr>
                <td><input type="radio" name="fundId" value="${fn:escapeXml(cusFund.fundId)}" required></td>
                <td>${fn:escapeXml(cusFund.fundName)}</td>
                <td>${fn:escapeXml(cusFund.ticker)}</td>
                <td class="text-right"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${cusFund.numOfShares}" /></td>
                <td class="text-right"><fmt:formatNumber value="${cusFund.price}" type="currency"/></td>
                <td class="text-right"><fmt:formatNumber value="${cusFund.value}" type="currency"/></td>
          </tr>
          </c:forEach>
        </table>
      </div>
      <div class="form-group">
        <label for="shares">Shares to sell</label>
        <input type="number" class="form-control" id="shares" name="shares" placeholder="12.345" step="0.001" min="0.001" required>
      </div>
      <div class="alert alert-info" role="alert">
        The transaction will be processed on the end of the trading day. The cash amount depends on the closing price of the fund at that time.
      </div>
      <button type="submit" class="btn btn-primary">Sell</button>
    </form>
  </main>
<%@ include file="footer.jsp" %>