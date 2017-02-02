package cfs.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
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
        try {
            BigDecimal pendingAmount = transactionDAO.pendingAmount(customer.getCustomerId());
            BigDecimal availableCash = Customer.amountFromDouble(customer.getCash()).add(pendingAmount);
            request.setAttribute("availableCash", availableCash);
        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }

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
                queueTransaction(customer.getCustomerId(), form.getAmountVal());
                request.setAttribute("message",
                        "Check requested. It will be processed by the end of the business day.");
                return "success.jsp";
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                return "request-check.jsp";
            }
        } else {
            return null;
        }
    }
    private void queueTransaction(int customerId, BigDecimal amount) throws Exception {
        try {
            Transaction.begin();
            Customer customer = customerDAO.read(customerId);
            BigDecimal pendingAmount = transactionDAO.pendingAmount(customerId);
            BigDecimal availableCash = Customer.amountFromDouble(customer.getCash()).add(pendingAmount);
            if (amount.compareTo(availableCash) > 0) {
                throw new Exception("Not enough available cash!");
            }
            //record a transaction
            Transactions transaction = new Transactions();
            transaction.setCustomerId(customer.getCustomerId());
            transaction.setType(Transactions.REQUEST_CHECK);
            transaction.setAmount(-amount.doubleValue());
            transaction.setStatus(Transactions.PENDING);
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
