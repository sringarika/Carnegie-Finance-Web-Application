<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<main>
    <h2>Research Funds</h2>
    <div class="alert alert-warning js-nochart-nojs">
      Loading the chart... If the chart does not show up, please check your Internet connection and refresh the page.
    </div>
    <div class="alert alert-info js-nochart-nodata" style="display: none;">
      There are not enough data to display the price chart. Please come back later when there are more data after a few transition days.
    </div>
    <div class="js-research-fund-chart"></div>
    
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
</main>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script>
google.charts.load('current', {packages: ['corechart', 'bar'], 'language': 'en'});
google.charts.setOnLoadCallback(function drawTrendlines() {
  document.querySelector('.js-nochart-nojs').style.display = 'none';
  document.querySelector('.js-nochart-nodata').style.display = 'block';
  
  var fundTables = document.querySelectorAll('.js-fund-table');
  if (fundTables.length == 0) return;
  
  var pricesForDate = {};
  var tickers = [];
  [].forEach.call(fundTables, function (table) {
    var ticker = table.getAttribute('data-ticker');
    var fundDateEls = table.querySelectorAll('.js-fund-date');
    var validDataCount = 0;
    [].forEach.call(fundDateEls, function (fundDateEl) {
      var priceEl = fundDateEl.parentElement.querySelector('.js-fund-price');
      if (!priceEl) return;
      var date = fundDateEl.getAttribute('data-date-iso');
      if (date === "N/A") return;
      validDataCount += 1;
      var price = parseFloat(priceEl.getAttribute('data-price'));
      var map = pricesForDate[date];
      if (!map) {
        pricesForDate[date] = map = {};
      }
      var dateDisp = fundDateEl.innerHTML || date;
      var priceDisp = priceEl.innerHTML || '$' + price.toFixed(2);
      // Try to escape ticker.
      var text = document.createTextNode(ticker);
      var span = document.createElement('span');
      span.appendChild(text);
      var tickerHtml = span.innerHTML.trim();
      var label = '<b>' + dateDisp.trim() + '</b><br>' + tickerHtml + ':<b>' + priceDisp.trim() + '</b>';
      label = '<div style="padding: 5px">' + label + '</div>';
      map[ticker] = [price, label];
    });
    if (validDataCount > 0) tickers.push(ticker);
  });
  
  if (Object.keys(pricesForDate).length === 0) return;

  var data = new google.visualization.DataTable();
  data.addColumn('date', 'Date');
  
  var trendlines = {};

  tickers.forEach(function (ticker, i) {
    data.addColumn('number', ticker);
    data.addColumn({type: 'string', role: 'tooltip', p: {html: true}});
    // trendlines[i] = {type: 'linear', lineWidth: 5, opacity: .3, pointSize: 0, pointsVisible: false, tooltip: false};
  });
  
  var rows = Object.keys(pricesForDate).map(function (date) {
    var parts = date.split('-')
    var row = [new Date(parseInt(parts[0]), parseInt(parts[1]) - 1, parseInt(parts[2]))];
    tickers.forEach(function (ticker) {
      var price = pricesForDate[date][ticker];
      if (typeof price !== 'undefined') {
        row.push(price[0]);
        row.push(price[1]);
      } else {
        row.push(null);
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
        format:'$#,##0.00',
      },
      animation: {
        duration: 500,
        startup: true,
      },
      pointSize: 2,
      height: 400,
      tooltip: {isHtml: true},
  };
  
  if (rows.length < 2) {
    var max = new Date(rows[0][0].getTime() + 1000 * 60 * 60 * 6);
    var min = new Date(rows[0][0].getTime() - 1000 * 60 * 60 * 6);
    options.hAxis.viewWindow = {max: max, min: min};
    delete options.trendlines;
  }

  var chart = new google.visualization.LineChart(document.querySelector('.js-research-fund-chart'));
  chart.draw(data, options);

  document.querySelector('.js-nochart-nojs').style.display = 'none';
  document.querySelector('.js-nochart-nodata').style.display = 'none';
});
</script>
<%@ include file="footer.jsp" %>