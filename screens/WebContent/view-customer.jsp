<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<%!
  public class Customeracct {
    private String fundName;
    private String shareno;    //need to change to double
    private String value;      //need to change to double(decimal format error)

    public Customeracct(String fundName, String shareno, String value) {
        this.fundName = fundName;
        this.shareno = shareno;
        this.value = value;
    }
    
    public String getFundName() {
        return fundName;
    }
    
    public String getShareNo() {
        return shareno;
    }
    
    public String getValue() {
        return value;
    }
    
  }
%>
<%

  Customeracct[] customeracct = {
          new Customeracct("UTI", "100.000", "100.00"),
          new Customeracct("SBI","10.000", "200.00"),
          new Customeracct("DSP Black Rock", "40.000","37.00"),
          };
  request.setAttribute("customeracct", customeracct);
%>


  <main>
    <h2>View Customer Account</h2>
    <table class="table table-striped table-bordered">
    <tr>
      <td>Name</td>
      <td>Johnny Bravo</td>
    </tr>
    <tr>
      <td>Address</td>
      <td>Elmer Palms Beach Street</td>
    </tr>
    <tr>
      <td>Last Trading Day</td>
      <td>01/19/2017</td>
    </tr>
    <tr>
      <td>Cash Balance</td>
      <td>$1,000,000.00</td>
    </tr>
    </table>
    
    <table class="table table-bordered table-striped">
      <tr>
        <th>Mutual Fund</th>
        <th>Number of Share</th>
        <th>Value</th>
      </tr>
      <c:forEach var="customeracct" items="${customeracct}">
        <tr>
          <td>${fn:escapeXml(customeracct.fundName)}</td>
          <td>${fn:escapeXml(customeracct.shareNo)}</td>
          <td>${fn:escapeXml(customeracct.value)}</td>
        </tr>
      </c:forEach>
    </table>
    
  </main>
<%@ include file="footer.jsp" %>