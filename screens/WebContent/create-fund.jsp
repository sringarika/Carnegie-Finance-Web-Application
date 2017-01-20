<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Create Fund</h2>
      <span class="label label-primary"></span>
      Fund Name: <input type="text" name="NewFundName" value="Google"><br>
      <br>
      Ticker: <input type="text" name="NewFundTicker" value="Googl"><br>
      <br>
      </p>
        <div class="alert alert-info" role="alert">
        The first closing day price for the fund is not provided until the next Transition Day.
      </div>
     <p>
      <a href="create.jsp" class="btn btn-primary" role="button">Create Fund</a>
    </p>
  </main>
<%@ include file="footer.jsp" %>