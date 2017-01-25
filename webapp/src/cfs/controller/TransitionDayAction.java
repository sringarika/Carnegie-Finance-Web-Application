package cfs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cfs.databean.Fund;
import cfs.formbean.TransitionDayForm;
import cfs.model.CustomerDAO;
import cfs.model.CustomerPositionDAO;
import cfs.model.FundPriceHistoryDAO;
import cfs.model.Model;
import cfs.model.TransactionDAO;

public class TransitionDayAction extends Action {

	// private FormBeanFactory<TransitionDayForm> formBeanFactory = FormBeanFactory.getInstance(TransitionDayForm.class);
	private TransactionDAO transactionDAO;
	private CustomerDAO customerDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	private CustomerPositionDAO customerPositionDAO;

    public TransitionDayAction(Model model) {
    	transactionDAO = model.getTransactionDAO();
    	customerDAO = model.getCustomerDAO();
    	fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
    }

    @Override
    public String getName() {
        return "transition-day.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        Fund[] funds = {
                new Fund("ebConsultants Fund", "EBIZC") {{ setFundId(1); }},
                new Fund("CMU Math Club Fund", "CMUMC") {{ setFundId(2); }},
                new Fund("Long-Term Investment Grade", "LTIG") {{ setFundId(3); }},
                new Fund("International Value", "IV") {{ setFundId(4); }},
                new Fund("Pennslyvania Long-Term Tax Exempt", "PLTTE") {{ setFundId(5); }},
                new Fund("High Yield Corporate", "HYC") {{ setFundId(6); }},
        };
        request.setAttribute("funds", funds);

        if (request.getMethod().equals("GET")) {
            return "transition-day.jsp";
        } else if (request.getMethod().equals("POST")) {
        	ArrayList<String> errors = new ArrayList<String>();
            request.setAttribute("errors", errors);
            try {
            	TransitionDayForm form = new TransitionDayForm(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                	errors.addAll(validationErrors);
                	return "transition-day.jsp";
                }
                String executeDate = form.getClosingDate();
                System.out.println("Closing: " + executeDate);
                Map<Integer, Double> prices = form.getPriceForFundMap();
                prices.forEach((fundId, closingPrice) -> {
                    System.out.println("\tFund " + fundId + ": " + closingPrice);
                });
                transactionDAO.processTransaction(prices, executeDate, customerDAO, customerPositionDAO);
                fundPriceHistoryDAO.updatePrice(prices, executeDate);
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
