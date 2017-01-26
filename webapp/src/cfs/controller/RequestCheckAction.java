package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import cfs.databean.Customer;
import cfs.databean.Transactions;
import cfs.formbean.RequestCheckForm;
import cfs.model.CustomerDAO;
import cfs.model.Model;
import cfs.model.TransactionDAO;

public class RequestCheckAction extends Action {
	FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory.getInstance(RequestCheckForm.class);
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	
    public RequestCheckAction(Model model) {
        customerDAO = model.getCustomerDAO();
        transactionDAO = model.getTransactionDAO();
    }

    @Override
    public String getName() {
        return "request-check.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        Customer customer = (Customer) request.getAttribute("customer");
        double pendingAmount;
        try {
            pendingAmount = transactionDAO.pendingAmount(customer.getCustomerId());
        } catch (RollbackException e1) {
            e1.printStackTrace();
            request.setAttribute("error", e1.getMessage());
            return "error.jsp";
        }
        double availableCash = pendingAmount + customer.getCash();
        request.setAttribute("availableCash", availableCash);
        if (request.getMethod().equals("GET")) {
            return "request-check.jsp";
        } else if (request.getMethod().equals("POST")) {
        	try {
				RequestCheckForm form = formBeanFactory.create(request);
				 List<String> validationErrors = form.getValidationErrors();
	                if (validationErrors.size() > 0) {
	                    request.setAttribute("error", validationErrors.get(0));
	                    return "request-check.jsp";
	            }
				double amount = form.getRequestAmount();
				queueTransaction(customer.getCustomerId(), amount);
	            request.setAttribute("message", "Check requested. It will be processed by the end of the business day.");
	            return "success.jsp";
			} catch (Exception e) {
			    e.printStackTrace();
                request.setAttribute("error", e.getMessage());
                return "request-check.jsp";
            }
        } else {
            return null;
        }
    }
    private void queueTransaction(int customerId, double amount) throws Exception {
        try {
            Transaction.begin();
            Customer customer = customerDAO.read(customerId);
            double pendingAmount = transactionDAO.pendingAmount(customer.getCustomerId());
            double availableCash = pendingAmount + customer.getCash();
            if (amount > availableCash) {
                throw new Exception("Not enough available cash!");
            } else if  (amount <= 0) {
                throw new Exception("Amount should be larger than 0!");
            }
            //record a transaction
            Transactions transaction = new Transactions();
            transaction.setCustomerId(customer.getCustomerId());
            transaction.setType("Request Check");
            transaction.setAmount(0 - amount);
            transaction.setStatus("Pending");
            transactionDAO.create(transaction);
            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
