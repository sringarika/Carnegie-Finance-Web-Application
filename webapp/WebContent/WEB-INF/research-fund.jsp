<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<main>
    <h2>Research Funds</h2>
    <h3>Latest Closing Price</h3>
    <table class="table table-bordered table-striped">
      <thead>
         <tr class="table-light-grey">
           <th>Fund Name</th>
           <th>Ticker</th>
           <th>Last Closing Date</th>
           <th class="text-right">Price</th>
         </tr>
      </thead>
      <c:forEach var="researchFund" items="${researchFundList}">
      <tr>
          <td>${fn:escapeXml(researchFund.fundName)}</td>
          <td>${fn:escapeXml(researchFund.ticker)}</td>
          <td>${fn:escapeXml(researchFund.lastClosingDate)}</td>
          <td class="text-right"><fmt:formatNumber value="${researchFund.price}" type="currency"/></td>
      </tr>
      </c:forEach>
    </table>
    <h3>Price History</h3>
    <div class="js-research-fund-chart"></div>
    <c:forEach var="priceHistoryMap" items="${mapList}" varStatus="status">
      <h4>${fn:escapeXml(researchFundList[status.index].fundName)}</h4>
      <table class="table table-bordered table-striped js-fund-table" data-ticker="${fn:escapeXml(researchFundList[status.index].ticker)}">
        <tr>
          <th>Date</th>
          <th class="text-right">Price</th>
        </tr>
        <c:forEach var="date" items="${dateList[status.index]}" varStatus="status2">
          <tr>
            <td class="js-fund-date" data-date-iso="${fn:escapeXml(date)}">
              ${fn:escapeXml(dateDispList[status.index][status2.index])}
            </td>
            <td class="text-right js-fund-price" data-price="${fn:escapeXml(priceHistoryMap.get(date))}">
              <fmt:formatNumber value="${priceHistoryMap.get(date)}" type="currency"/>
            </td>
          </tr>
        </c:forEach>
      </table>
    </c:forEach>
    <h3>${fn:escapeXml(message)}</h3>
</main>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script>
google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(function drawTrendlines() {
  var fundTables = document.querySelectorAll('.js-fund-table');
  if (fundTables.length == 0) return;
  
  var pricesForDate = {};
  var tickers = [];
  [].forEach.call(fundTables, function (table) {
    var ticker = table.getAttribute('data-ticker');
    tickers.push(ticker);
    var fundDateEls = table.querySelectorAll('.js-fund-date');
    [].forEach.call(fundDateEls, function (fundDateEl) {
      var priceEl = fundDateEl.parentElement.querySelector('.js-fund-price');
      if (!priceEl) return;
      var date = fundDateEl.getAttribute('data-date-iso');
      var price = parseFloat(priceEl.getAttribute('data-price'));
      var map = pricesForDate[date];
      if (!map) {
        pricesForDate[date] = map = {};
      }
      map[ticker] = price;
    });
  });

  var data = new google.visualization.DataTable();
  data.addColumn('date', 'Date');
  
  var trendlines = {};

  tickers.forEach(function (ticker, i) {
    data.addColumn('number', ticker);
    // trendlines[i] = {type: 'linear', lineWidth: 5, opacity: .3, pointSize: 0, pointsVisible: false, tooltip: false};
  });
  
  var rows = Object.keys(pricesForDate).map(function (date) {
    var parts = date.split('-')
    var row = [new Date(parseInt(parts[0]), parseInt(parts[1]) - 1, parseInt(parts[2]))];
    tickers.forEach(function (ticker) {
      var price = pricesForDate[date][ticker];
      if (typeof price == 'number') {
        row.push(price);
      } else {
        row.push(null);
      }
    });
    return row;
  });
  data.addRows(rows);
  
  var options = {
      title: 'Price History For Funds',
      trendlines: trendlines,
      hAxis: {
        title: 'Date',
        format: 'MM/dd/yyyy',
        gridlines: {count: 10},
      },
      vAxis: {
        title: 'Closing Price',
      },
      animation: {
        duration: 500,
        startup: true,
      },
      pointSize: 2,
      height: 400,
  };
  
  if (rows.length < 2) {
    var max = new Date(rows[0][0].getTime() + 1000 * 60 * 60 * 6);
    var min = new Date(rows[0][0].getTime() - 1000 * 60 * 60 * 6);
    options.hAxis.viewWindow = {max: max, min: min};
    delete options.trendlines;
  }

  var chart = new google.visualization.LineChart(document.querySelector('.js-research-fund-chart'));
  chart.draw(data, options);
  
  var columns = [];
  var series = {};
  for (var i = 0; i < data.getNumberOfColumns(); i++) {
      columns.push(i);
      if (i > 0) {
          series[i - 1] = {};
      }
  }

  var view = new google.visualization.DataView(data);
  // view.hideColumns([]);
  chart.draw(view, options);
});
</script>
<%@ include file="footer.jsp" %>