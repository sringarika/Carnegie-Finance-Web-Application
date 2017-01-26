package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
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
        if (request.getMethod().equals("GET")) {
            double availableCash = customer.getCash();
            request.setAttribute("availableCash", availableCash);
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
				if (amount >= customer.getCash()) {
				    request.setAttribute("error", "Not enough cash to withdraw!");
				    return "request-check.jsp";
				}
				customerDAO.deductCash(amount, customer.getCustomerId());
	            request.setAttribute("message", "Check requested. It will be processed by the end of the business day.");
	            //record a transaction
	            Transactions transaction = new Transactions();
	            transaction.setCustomerId(customer.getCustomerId());
	            transaction.setType("Request Check");
	            transaction.setAmount(amount);
	            transaction.setStatus("Pending");
	            transactionDAO.create(transaction);
	            return "success.jsp";
			} catch (FormBeanException e) {
			    request.setAttribute("error", e.getMessage());
                return "request-check.jsp";
			} catch (RollbackException e) {
			    request.setAttribute("error", e.getMessage());
                return "request-check.jsp";
            }
        } else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
