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
import cfs.model.CustomerDAO;
import cfs.model.CustomerPositionDAO;
import cfs.model.FundDAO;
import cfs.model.FundPriceHistoryDAO;
import cfs.model.Model;
import cfs.model.TransactionDAO;

public class TransitionDayAction extends Action {

	// private FormBeanFactory<TransitionDayForm> formBeanFactory = FormBeanFactory.getInstance(TransitionDayForm.class);
	private TransactionDAO transactionDAO;
	private CustomerDAO customerDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	private CustomerPositionDAO customerPositionDAO;
    private FundDAO fundDAO;

    public TransitionDayAction(Model model) {
    	transactionDAO = model.getTransactionDAO();
    	customerDAO = model.getCustomerDAO();
    	fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
    	fundDAO = model.getFundDAO();
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
            String lastClosingDate = "2017-01-17"; // TODO
            LocalDate date = LocalDate.parse(lastClosingDate, DateTimeFormatter.ISO_DATE);
            LocalDate minDate = date.plusDays(1);
            request.setAttribute("lastClosingDateDisp",
                    date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", new Locale("en", "US"))));
            request.setAttribute("minClosingDateISO", minDate.format(DateTimeFormatter.ISO_DATE));
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
                String executeDate = form.getClosingDate();
                Map<Integer, Double> prices = form.getPriceForFundMap();

                System.out.println("Closing: " + executeDate);
                prices.forEach((fundId, closingPrice) -> {
                    System.out.println("\tFund " + fundId + ": " + closingPrice);
                });

                fundPriceHistoryDAO.updatePrice(prices, executeDate);
                // TODO: Do not pass prices and executeDate into processTransaction.
                transactionDAO.processTransaction(prices, executeDate, customerDAO, customerPositionDAO);
            } catch (Exception e) {
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
