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
    
    public Customer(String userName, String firstName, String lastName) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
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
          new Customer("bobama", "Barack", "Obama")
          };
  request.setAttribute("customers", customers);
%>
  <main>
      <div class="form-group">
     <b>Deposit Check for</b>: Barack Obama
     </div>
     <br>
 
	<form action="deposit-check.do" method="POST">
        <label for="amount">Amount to be deposited(in dollars)</label>
        <div class="input-group">
          <div class="input-group-addon">$</div>
          <input type="String" class="form-control" id="amount" name="amount" placeholder="1.00" step="0.01" min="0.01" required>
        </div>
      <br>
        <c:if test="${(!empty error)}">
      <div class="alert alert-danger">
        ${fn:escapeXml(error)}
      </div>
    </c:if>
    <br>
    
      <div class="alert alert-info" role="alert">
        The transaction will be processed on the end of the trading day.
      </div>
     <p>
           <button type="submit" class="btn btn-primary">Deposit Check</button>
    </p>
    </form>
  </main>
<%@ include file="footer.jsp" %>