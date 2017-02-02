package cfs.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import cfs.databean.Customer;
import cfs.model.CustomerDAO;
import cfs.model.Model;
import cfs.model.TransactionDAO;
import cfs.viewbean.TransactionHistoryView;

public class TransactionHistoryAction extends Action {

    private TransactionDAO transactionDAO;
    private CustomerDAO customerDAO;

    public TransactionHistoryAction(Model model) {
        transactionDAO = model.getTransactionDAO();
        customerDAO = model.getCustomerDAO();
    }

    @Override
    public String getName() {
        return "transaction-history.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int customerId;
        if (session.getAttribute("employeeId") != null) {
            String customerIdStr = request.getParameter("customerId");
            try {
                customerId = Integer.parseInt(customerIdStr);
            } catch (Exception e) {
                request.setAttribute("error", "Invalid customerId!");
                return "error.jsp";
            }
        } else {
            customerId = (int) session.getAttribute("customerId");
            request.setAttribute("isMyAccount", true);
        }
        try {
            TransactionHistoryView[] transactions = transactionDAO.showHistoryView(customerId);
            Customer customer = customerDAO.read(customerId);
            String firstName = customer.getFirstname();
            String lastName = customer.getLastname();
            BigDecimal pendingAmount = transactionDAO.pendingAmount(customerId);
            BigDecimal availableCash = Customer.amountFromDouble(customer.getCash()).add(pendingAmount);
            double cashBalance = customer.getCash();
            request.setAttribute("availableCash", availableCash);
            request.setAttribute("cashBalance", cashBalance);
            request.setAttribute("transactions", transactions);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            return "transaction-history.jsp";
        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.User;
    }
}
