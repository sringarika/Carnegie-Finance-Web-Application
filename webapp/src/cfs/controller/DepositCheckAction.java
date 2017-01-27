package cfs.controller;
//TO-DO Error check
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.Transaction;
import org.mybeans.form.FormBeanFactory;

import cfs.databean.Customer;
import cfs.databean.Transactions;
import cfs.formbean.DepositCheckForm;
import cfs.model.CustomerDAO;
import cfs.model.Model;
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
            return "deposit-check.jsp";
        } else if (request.getMethod().equals("POST")) {
        	try {
                DepositCheckForm form = FormBeanFactory.getInstance(DepositCheckForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                System.out.println("checkpoint 1 with error size: "+validationErrors.size());
                if (validationErrors.size() > 0) {
                    throw new Exception(validationErrors.get(0));
                }
                System.out.println("now here");
            queueTransaction(customerId,form);
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
    
    private void queueTransaction(int customerId,DepositCheckForm form) throws Exception {
        try {
            double amount = form.getRequestAmount();
            //record a transaction
            System.out.println("amount is "+amount);
            Transaction.begin();
			Transactions transaction = new Transactions();
            transaction.setCustomerId(form.getCustomerIdVal());
            transaction.setType("Deposit Check");
            transaction.setAmount(amount);
            transaction.setStatus("Pending");
            transactionDAO.create(transaction);
            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }
    
    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
