<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Transaction History</h2>
    <table class="table table-bordered table-striped">
  	  <thead>
	  <tr>
	  	<th>ID</th>
	    <th>Transaction Date</th>
	    <th>Transaction Type</th>
	    <th>Fund</th>
	    <th>Shares</th>
	    <th>Price</th>
	    <th>Total Amount</th>
	    <th>Transaction Status</th>
	  </tr>
	  </thead>
	  <tbody>
	  <tr>
	  	<td>1</td>
	    <td>01/19/2017</td>
	    <td>Buy</td>
	    <td>EBIZC - ebConsultants Fund</td>
	    <td>2.000</td>
	    <td>$12.34</td>
	    <td>$24.68</td>
	    <td>Finished</td>
	  </tr>
	  <tr>
	  	<td>2</td>
	    <td>01/19/2017</td>
	    <td>Buy</td>
	    <td>EBIZC - ebConsultants Fund</td>
	    <td></td>
	    <td>$12.34</td>
	    <td>$0.10</td>
	    <td>Rejected</td>
	  </tr>
	  <tr>
	  	<td>3</td>
	    <td></td>
	    <td>Buy</td>
	    <td>CMUMC - CMU Math Club Fund</td>
	    <td></td>
	    <td></td>
	    <td>$123.40</td>
	    <td>Pending</td>
	  </tr>
	  </tbody>
	</table>
  </main>
<%@ include file="footer.jsp" %>
