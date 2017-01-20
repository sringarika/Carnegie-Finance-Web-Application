<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <head>
<style>
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
}
th, td {
    padding: 5px;
    text-align: left;
}
</style>
</head>


  <main>
    <h2>View Customer Account</h2>
    <p>
      <span class="label label-primary"></span>
      Name : John Doe
      <br>
      Address: 123 Elmer Street
      <br>
      Last Trading Day: 01/19/2017
      <br>
      Cash Balance: 0$
      <br>
      
  <table style="width:100%">
  <tr>
    <th>Mutual Fund</th>
    <th>Number of Shares</th> 
    <th>Value</th>
  </tr>
  <tr>
    <th>UTI Mutual Fund</th>
    <th>100</th> 
    <th>$1000</th>
  </tr>
  <tr>
    <th>Black Rock Mutual Fund</th>
    <th>10</th> 
    <th>$100</th>
  </tr>
  <tr>
    <th>SBI Mutual Fund</th>
    <th>40</th> 
    <th>$567.01</th>
  </tr>
  <tr>
    <th>CitiBank Mutual Fund</th>
    <th>400</th> 
    <th>$2785.81</th>
  </tr>
  </table>
      
    </p>
  </main>
<%@ include file="footer.jsp" %>