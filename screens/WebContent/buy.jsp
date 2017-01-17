<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
  <main>
    <h2>Buy Funds</h2>
    <p>
      <span class="label label-primary">TODO</span>
      Logged-in customers can buy shares a mutual fund. These funds are
created by employees. Customers can buy shares in funds they do not already own
as well as additional shares in a fund they already own. The customer specifies
the fund he wishes to purchase and the dollar amount. The number of shares
purchased depends on the closing price of the fund that night. The money used to
buy the shares comes out of the buying customer's cash balance when the
transaction is processed. (Cash is created when an employee deposits into a
customer's account.) You must ensure that this transaction (plus other pending
buy orders and check requests) will not cause the cash balance to go negative. If
there are no errors, the buy order is queued as a pending transaction. (See the
“Transition Day” use case for details on the processing of pending transactions.) 
    </p>
  </main>
<%@ include file="footer.jsp" %>