package cfs.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import cfs.databean.Transactions;
import cfs.model.CustomerDAO;
import cfs.model.CustomerPositionDAO;
import cfs.model.Model;
import cfs.model.TransactionDAO;
import cfs.viewbean.PositionView;

public class ViewCustomerAction extends Action {

    private CustomerPositionDAO customerPositionDAO;
    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;

    public ViewCustomerAction(Model model) {
        customerPositionDAO = model.getCustomerPositionDAO();
        customerDAO = model.getCustomerDAO();
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
            String lastTradingDate = null;
            // TODO: Does transaction type need to be Buy/Sell?
            Transactions[] transactions = transactionDAO.match(
                    MatchArg.equals("customerId", customerId),
                    MatchArg.notEquals("status", "Pending"));
            if (transactions != null && transactions.length > 0) {
                for (int i = 0; i < transactions.length; i++) {
                    String executeDate = transactions[i].getExecuteDate();
                    if (executeDate != null && (lastTradingDate == null || executeDate.compareTo(lastTradingDate) > 0)) {
                        lastTradingDate = executeDate;
                    }
                }
            }
            if (lastTradingDate != null) {
                LocalDate date = LocalDate.parse(lastTradingDate, DateTimeFormatter.ISO_DATE);
                request.setAttribute("lastTradingDateDisp",
                        date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", new Locale("en", "US"))));
            } else {
                request.setAttribute("lastTradingDateDisp", "N/A");
            }
            request.setAttribute("showCustomer", customerDAO.read(customerId));
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
