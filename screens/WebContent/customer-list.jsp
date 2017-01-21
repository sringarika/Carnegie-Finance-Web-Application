<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<%!
  public class Customer {
    private int customerId;
    private String userName;
    private String firstName;
    private String lastName;
    
    public Customer(int customerId, String userName, String firstName, String lastName) {
        this.customerId = customerId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public String getUsername() {
        return userName;
    }
    
    public String getFirstname() {
        return firstName;
    }
    
    public String getLastname() {
        return lastName;
    }
  }
%>
<%
  Customer[] customers = {
          new Customer(1, "bobama", "Barack", "Obama"),
          new Customer(2, "hclinton", "Hillary", "Clinton"),
          new Customer(3, "makeamericagreatagain", "Donald", "Trump"),
          };
  request.setAttribute("customers", customers);
%>
  <main>
    <h2>Customer List</h2>
    
    <table class="table table-bordered table-striped">
      <tr>
        <th>ID</th>
        <th>Username</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Action</th>
      </tr>
      <c:forEach var="customer" items="${customers}">
        <tr>
          <td>${fn:escapeXml(customer.customerId)}</td>
          <td>${fn:escapeXml(customer.username)}</td>
          <td>${fn:escapeXml(customer.firstname)}</td>
          <td>${fn:escapeXml(customer.lastname)}</td>
          <td>
            <a class="btn btn-primary" href="view-customer.jsp?customerId=${fn:escapeXml(customer.customerId)}">View Details</a>
            <a class="btn btn-info" href="transaction-history.jsp?customerId=${fn:escapeXml(customer.customerId)}">Transaction History</a>
            <a class="btn btn-warning" href="reset-password.jsp?customerId=${fn:escapeXml(customer.customerId)}">Reset Password</a>
            <a class="btn btn-danger" href="deposit-check.jsp?customerId=${fn:escapeXml(customer.customerId)}">Deposit Check</a>
            
          </td>
        </tr>
      </c:forEach>
    </table>
  </main>
<%@ include file="footer.jsp" %>