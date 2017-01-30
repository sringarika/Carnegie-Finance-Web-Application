package cfs.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import cfs.databean.Customer;
import cfs.model.CustomerDAO;
import cfs.model.CustomerPositionDAO;
import cfs.model.FundPriceHistoryDAO;
import cfs.model.Model;
import cfs.model.TransactionDAO;
import cfs.viewbean.PositionView;

public class ViewCustomerAction extends Action {

    private CustomerPositionDAO customerPositionDAO;
    private CustomerDAO customerDAO;
    private FundPriceHistoryDAO fundPriceHistoryDAO;
    private TransactionDAO transactionDAO;

    public ViewCustomerAction(Model model) {
        customerPositionDAO = model.getCustomerPositionDAO();
        customerDAO = model.getCustomerDAO();
        fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
        transactionDAO = model.getTransactionDAO();
    }

    @Override
    public String getName() {
        return "view-customer.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        int customerId;
        String customerIdStr = request.getParameter("customerId");
        try {
            customerId = Integer.parseInt(customerIdStr);
        } catch (Exception e) {
            request.setAttribute("error", "Invalid customerId!");
            return "error.jsp";
        }
        return showCustomer(customerId, request);
    }

    protected String showCustomer(int customerId, HttpServletRequest request) {
        try {
            String lastTradingDate = fundPriceHistoryDAO.getLastClosingDate();
            if (lastTradingDate != null) {
                LocalDate date = LocalDate.parse(lastTradingDate, DateTimeFormatter.ISO_DATE);
                request.setAttribute("lastTradingDateDisp",
                        date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", new Locale("en", "US"))));
            } else {
                request.setAttribute("lastTradingDateDisp", "N/A");
            }
            Customer customer = customerDAO.read(customerId);
            request.setAttribute("showCustomer", customer);

            BigDecimal pendingAmount = transactionDAO.pendingAmount(customerId);
            BigDecimal availableCash = Customer.amountFromDouble(customer.getCash()).add(pendingAmount);
            request.setAttribute("availableCash", availableCash);

            PositionView[] positions = customerPositionDAO.getPositionViews(customerId);
            request.setAttribute("positions", positions);
            return "view-customer.jsp";
        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
