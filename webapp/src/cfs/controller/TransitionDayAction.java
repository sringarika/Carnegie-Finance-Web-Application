package cfs.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import cfs.databean.Customer;
import cfs.databean.Fund;
import cfs.databean.FundPriceHistory;
import cfs.databean.Transactions;
import cfs.formbean.TransitionDayForm;
import cfs.model.FundDAO;
import cfs.model.FundPriceHistoryDAO;
import cfs.model.Model;
import cfs.model.TransactionDAO;
import cfs.model.TransactionProcessor;

public class TransitionDayAction extends Action {
    private FundPriceHistoryDAO fundPriceHistoryDAO;
    private FundDAO fundDAO;
    private TransactionProcessor transactionProcessor;
    private TransactionDAO transactionDAO;

    public TransitionDayAction(Model model) {
        fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
        fundDAO = model.getFundDAO();
        transactionProcessor = model.getTransactionProcessor();
        transactionDAO = model.getTransactionDAO();
    }

    @Override
    public String getName() {
        return "transition-day.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        try {
            Transactions[] transactions = transactionDAO.match(MatchArg.equals("status", Transactions.PENDING));
            if (transactions == null) {
                request.setAttribute("pendingTransactionCount", 0);
            } else {
                request.setAttribute("pendingTransactionCount", transactions.length);
            }
            Fund[] funds = fundDAO.match();
            request.setAttribute("funds", funds);

            Map<Integer, BigDecimal> lastPriceForFund = new HashMap<Integer, BigDecimal>();
            Map<Integer, BigDecimal> minPriceForFund = new HashMap<Integer, BigDecimal>();
            Map<Integer, BigDecimal> maxPriceForFund = new HashMap<Integer, BigDecimal>();

            FundPriceHistory[] lastPrices = fundPriceHistoryDAO.match(MatchArg.max("executeDate"));
            if (lastPrices != null && lastPrices.length > 0) {
                String lastClosingDate = lastPrices[0].getExecuteDate();
                request.setAttribute("lastClosingDateISO", lastClosingDate);
                LocalDate date = LocalDate.parse(lastClosingDate, DateTimeFormatter.ISO_DATE);
                LocalDate minDate = date.plusDays(1);
                request.setAttribute("lastClosingDateDisp",
                        date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", new Locale("en", "US"))));
                request.setAttribute("minClosingDateISO", minDate.format(DateTimeFormatter.ISO_DATE));

                for (FundPriceHistory price : lastPrices) {
                    BigDecimal lastPrice = Customer.amountFromDouble(price.getPrice());
                    lastPriceForFund.put(price.getFundId(), lastPrice);
                }
            } else {
                request.setAttribute("lastClosingDateISO", "N/A");
                request.setAttribute("lastClosingDateDisp", "N/A");
                LocalDate minDate = LocalDate.now();
                request.setAttribute("minClosingDateISO", minDate.format(DateTimeFormatter.ISO_DATE));
            }
            for (Fund fund : funds) {
                BigDecimal lastPrice = lastPriceForFund.get(fund.getFundId());
                BigDecimal minPrice = new BigDecimal("10.00");
                BigDecimal maxPrice = new BigDecimal("1000.00");
                if (lastPrice != null) {
                    BigDecimal halfPrice = lastPrice.divide(new BigDecimal("2"), 2, RoundingMode.CEILING);
                    BigDecimal doublePrice = lastPrice.multiply(new BigDecimal("2")).setScale(2, RoundingMode.FLOOR);
                    // No more than double, no less than half, but still capped at the range.
                    if (halfPrice.compareTo(minPrice) > 0) {
                        minPrice = halfPrice;
                    }
                    if (doublePrice.compareTo(maxPrice) < 0) {
                        maxPrice = doublePrice;
                    }
                }
                minPriceForFund.put(fund.getFundId(), minPrice);
                maxPriceForFund.put(fund.getFundId(), maxPrice);
            }
            request.setAttribute("lastPriceForFund", lastPriceForFund);
            request.setAttribute("minPriceForFund", minPriceForFund);
            request.setAttribute("maxPriceForFund", maxPriceForFund);

        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
        if (request.getMethod().equals("GET")) {
            return "transition-day.jsp";
        } else if (request.getMethod().equals("POST")) {
            try {
                TransitionDayForm form = new TransitionDayForm(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    request.setAttribute("error", validationErrors.get(0));
                    return "transition-day.jsp";
                }
                String lastTransitionDate = form.getLastClosingDate();
                String transitionDate = form.getClosingDate();
                Map<Integer, BigDecimal> prices = form.getPriceForFundMap();

                fundPriceHistoryDAO.updatePrice(prices, transitionDate, lastTransitionDate, fundDAO);
                transactionProcessor.transitionDay(transitionDate);
                transactionProcessor.processPendingTransactions();
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", e.getMessage());
                return "transition-day.jsp";
            }
            request.setAttribute("message", "Business day has ended. Transactions executed successfully.");
            return "success.jsp";
        } else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
