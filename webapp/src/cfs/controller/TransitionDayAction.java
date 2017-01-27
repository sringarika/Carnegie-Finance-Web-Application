package cfs.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import cfs.databean.Fund;
import cfs.formbean.TransitionDayForm;
import cfs.model.FundDAO;
import cfs.model.FundPriceHistoryDAO;
import cfs.model.Model;
import cfs.model.TransactionProcessor;

public class TransitionDayAction extends Action {
	private FundPriceHistoryDAO fundPriceHistoryDAO;
    private FundDAO fundDAO;
    private TransactionProcessor transactionProcessor;

    public TransitionDayAction(Model model) {
    	fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
    	fundDAO = model.getFundDAO();
    	transactionProcessor = model.getTransactionProcessor();
    }

    @Override
    public String getName() {
        return "transition-day.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        try {
            Fund[] funds = fundDAO.match();
            request.setAttribute("funds", funds);
            String lastClosingDate = fundPriceHistoryDAO.getLastClosingDate();
            if (lastClosingDate != null) {
                request.setAttribute("lastClosingDateISO", lastClosingDate);
                LocalDate date = LocalDate.parse(lastClosingDate, DateTimeFormatter.ISO_DATE);
                LocalDate minDate = date.plusDays(1);
                request.setAttribute("lastClosingDateDisp",
                        date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", new Locale("en", "US"))));
                request.setAttribute("minClosingDateISO", minDate.format(DateTimeFormatter.ISO_DATE));
            } else {
                request.setAttribute("lastClosingDateISO", "N/A");
                request.setAttribute("lastClosingDateDisp", "N/A");
                LocalDate minDate = LocalDate.now();
                request.setAttribute("minClosingDateISO", minDate.format(DateTimeFormatter.ISO_DATE));
            }
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
                Map<Integer, Double> prices = form.getPriceForFundMap();

                System.out.println("Transition: " + lastTransitionDate + " -> " + transitionDate);
                prices.forEach((fundId, closingPrice) -> {
                    System.out.println("\tFund " + fundId + ": " + closingPrice);
                });

                fundPriceHistoryDAO.updatePrice(prices, transitionDate, lastTransitionDate);
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
