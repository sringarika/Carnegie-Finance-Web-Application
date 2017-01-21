<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

    <h2>Deposit Check</h2>
    <br>
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
            <a class="btn btn-primary" href="?customerId=${fn:escapeXml(customer.customerId)}">Select Customer</a>
          </td>
        </tr>
      </c:forEach>
    </table>
    
      <div class="form-group">
        <label for="amount">Amount to be deposited(in dollars)</label>
        <div class="input-group">
          <div class="input-group-addon">$</div>
          <input type="number" class="form-control" id="amount" name="amount" placeholder="1.00" step="0.01" min="0.01" required>
        </div>
      <br>
      </p>
      <div class="alert alert-info" role="alert">
        The transaction will be processed on the end of the trading day.
      </div>
     <p>
      <a href="confirmdepositcheck.jsp" class="btn btn-primary" role="button">Deposit Check</a>
    </p>
    
  </main>
<%@ include file="footer.jsp" %>