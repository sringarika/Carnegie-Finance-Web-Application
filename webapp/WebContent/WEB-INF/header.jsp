<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>CFS Mutual Funds</title>
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
          <c:if test='${!empty links}'>
            <ul class="nav navbar-nav">
              <c:forEach var="link" items="${links}">
                <c:choose>
                  <c:when test="${link.key == '(dropdown)'}">
                    <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${fn:escapeXml(link.value)} <span class="caret"></span></a>
                      <ul class="dropdown-menu">
                        <li><a href="create-customer.do">Create Customer Account</a></li>
                        <li><a href="create-employee.do">Create Employee Account</a></li>
                      </ul>
                    </li>
                  </c:when>
                  <c:when test="${link.key == activeLink}">
                    <li class="active"><a href="${fn:escapeXml(link.key)}">${fn:escapeXml(link.value)} <span class="sr-only">(current)</span></a></li>
                  </c:when>
                  <c:otherwise>
                    <li><a href="${fn:escapeXml(link.key)}">${fn:escapeXml(link.value)}</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
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