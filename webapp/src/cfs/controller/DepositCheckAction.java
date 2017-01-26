package cfs.controller;
//TO-DO Error check
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.Transaction;
import org.mybeans.form.FormBeanFactory;

import cfs.databean.Customer;
import cfs.databean.Transactions;
import cfs.formbean.DepositCheckForm;
import cfs.model.Model;
import cfs.model.CustomerDAO;
import cfs.model.TransactionDAO;

public class DepositCheckAction extends Action {
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;

	   public DepositCheckAction(Model model) {
	        customerDAO = model.getCustomerDAO();
	        transactionDAO = model.getTransactionDAO();
	    }

    @Override
    public String getName() {
        return "deposit-check.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
    	Customer customer = (Customer) request.getAttribute("customer");
        int customerId;
        String customerIdStr = request.getParameter("customerId");
        try {
            customerId = Integer.parseInt(customerIdStr);
        } catch (Exception e) {
            request.setAttribute("error", "Invalid customerId!");
            return "error.jsp";
        }
        request.setAttribute("customerId", customerId);
        if (request.getMethod().equals("GET")) {
        	double availableCash = customer.getCash();
        	request.setAttribute("availableCash", availableCash);
            return "deposit-check.jsp";
        } else if (request.getMethod().equals("POST")) {
        //	System.out.println(request.getParameter("amount"));
        	try {
                DepositCheckForm form = FormBeanFactory.getInstance(DepositCheckForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    throw new Exception(validationErrors.get(0));
                }
                double amount = form.getRequestAmount();
				customerDAO.increaseCash(amount, customer.getCustomerId());
				
				//Add to pending list
				Transaction.begin();
				Transactions transaction = new Transactions();
	            transaction.setCustomerId(customer.getCustomerId());
	            transaction.setType("Deposit Check");
	            transaction.setAmount(amount);
	            transaction.setStatus("Pending");
	            transactionDAO.create(transaction);
	            Transaction.commit();
            request.setAttribute("message", "Check deposited successfully! The transaction will be processed by the end of the business day.");
            return "success.jsp";
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            return "deposit-check.jsp";
        }
        }else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
