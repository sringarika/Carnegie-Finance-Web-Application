<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>CFS Mutual Funds</title>
  <link rel="stylesheet" href="site.css" media="all">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

  <script src="https://code.jquery.com/jquery-3.1.1.min.js" integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8=" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
  <header>
    <nav class="navbar navbar-inverse" style="border-radius: 0;">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#main-nav-bar" aria-expanded="false">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">CFS Mutual Funds</a>
        </div>
        <div class="collapse navbar-collapse" id="main-nav-bar">
          <c:if test="${empty customer and empty employee}">
            <ul class="nav navbar-nav">
              <li class="active"><a href="login.do">Login <span class="sr-only">(current)</span></a></li>
            </ul>
          </c:if>
          <c:if test='${!empty customer}'>
            <ul class="nav navbar-nav">
              <li class="active"><a href="account.do">Account <span class="sr-only">(current)</span></a></li>
              <li><a href="buy.do">Buy</a></li>
              <li><a href="sell.do">Sell</a></li>
              <li><a href="request-check.do">Request Check</a></li>
              <li><a href="transaction-history.do">Transaction History</a></li>
              <li><a href="research-fund.do">Research Funds</a></li>
            </ul>
          </c:if>
          <c:if test='${!empty employee}'>
            <ul class="nav navbar-nav">
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Create Account <span class="caret"></span></a>
                <ul class="dropdown-menu">
                  <li><a href="create-customer-account.do">Create Customer Account</a></li>
                  <li><a href="create-employee-account.do">Create Employee Account</a></li>
                </ul>
              </li>
              <li><a href="deposit-check.do">Deposit Check</a></li>
              <li><a href="create-fund.do">Create Fund</a></li>
              <li><a href="transition-day.do">Transition Day</a></li>
              <li><a href="customer-list.do">Manage Customers</a></li>
            </ul>
          </c:if>
          <c:if test="${!empty customer or !empty employee}">
            <ul class="nav navbar-nav navbar-right">
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                  <span class="glyphicon glyphicon-user"></span>
                  ${fn:escapeXml(greeting)}
                  <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                  <li><a href="change-password.do"><span class="glyphicon glyphicon-lock"></span> Change Password</a></li>
                  <li><a href="logout.do"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>
              </li>
            </ul>
          </c:if>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>
  </header>
  <div class="container">