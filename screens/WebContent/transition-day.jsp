<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Transition Day</h2>
    <p class="alert alert-info">
      There are <a href="transaction-list.jsp">42 transactions</a> pending.
      <a href="transaction-list.jsp">Show details...</a>
    </p>
    <div>
      <p>Last Closing Date: 01/19/2017</p>
      <p>Current Closing Date: <input type="text" class="form-control" name="price" value=""></p>
    </div>
    <table class="table table-striped table-bordered">
    <tr>
      <th>Fund ID</th>
      <th>Fund Name</th>
      <th>Ticker</th>
      <th>Last Closing Price</th>
    </tr>
    <tr>
      <td>1</td>
      <td>ebConsultants Fund</td>
      <td>EBIZC</td>
      <td><input type="text" class="form-control" name="price" value=""></td>
    </tr>
    <tr>
      <td>2</td>
      <td>CMU Math Club Fund</td>
      <td>CMUMC</td>
      <td><input type="text" class="form-control" name="price" value=""></td>
    </tr>
    </table>
    <button type="submit" class="btn btn-primary">Transition Day</button>
  </main>
<%@ include file="footer.jsp" %>