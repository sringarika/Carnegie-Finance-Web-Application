package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import cfs.databean.Customer;
import cfs.databean.Transactions;
import cfs.model.Model;
import cfs.model.TransactionDAO;

public class TransactionHistoryAction extends Action {
	
	private TransactionDAO transactionDAO;

    public TransactionHistoryAction(Model model) {
    	transactionDAO = model.getTransactionDAO();
    }

    @Override
    public String getName() {
        return "transaction-history.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        int customerId;
        if (request.getAttribute("employee") != null) {
            String customerIdStr = request.getParameter("customerId");
            try {
                customerId = Integer.parseInt(customerIdStr);
            } catch (Exception e) {
                request.setAttribute("error", "Invalid customerId!");
                return "error.jsp";
            }
        } else {
            Customer customer = (Customer) request.getAttribute("customer");
            customerId = customer.getCustomerId();
        }
        try {
			Transactions[] history= transactionDAO.showHistory(customerId);
			request.setAttribute("history", history);
		} catch (RollbackException e) {
			request.setAttribute("error", e.getMessage());
            return "transaction-history.jsp";
		}
        return "transaction-history.jsp";
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.User;
    }
}
