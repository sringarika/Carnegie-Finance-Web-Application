package cfs.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanFactory;

import cfs.databean.Customer;
import cfs.databean.Fund;
import cfs.databean.Transactions;
import cfs.formbean.BuyFundForm;
import cfs.model.CustomerDAO;
import cfs.model.FundDAO;
import cfs.model.Model;
import cfs.model.TransactionDAO;

public class BuyFundAction extends Action {

    private TransactionDAO transactionDAO;
    private CustomerDAO customerDAO;
    private FundDAO fundDAO;

    public BuyFundAction(Model model) {
        transactionDAO = model.getTransactionDAO();
        customerDAO = model.getCustomerDAO();
        fundDAO = model.getFundDAO();
    }

    @Override
    public String getName() {
        return "buy-fund.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        Customer customer = (Customer) request.getAttribute("customer");

        try {
            BigDecimal pendingAmount = transactionDAO.pendingAmount(customer.getCustomerId());
            BigDecimal availableCash = Customer.amountFromDouble(customer.getCash()).add(pendingAmount);
            request.setAttribute("availableCash", availableCash);
            Fund[] funds = fundDAO.match();
            request.setAttribute("funds", funds);
        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }

        if (request.getMethod().equals("GET")) {
            return "buy-fund.jsp";
        } else if (request.getMethod().equals("POST")) {
            try {
                BuyFundForm form = FormBeanFactory.getInstance(BuyFundForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    throw new Exception(validationErrors.get(0));
                }
                queueTransaction(customer.getCustomerId(), form.getFundIdVal(), form.getAmountVal());
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                return "buy-fund.jsp";
            }
            request.setAttribute("message", "Transaction scheduled. It will be processed by the end of the business day.");
            return "success.jsp";
        } else {
            return null;
        }
    }

    private void queueTransaction(int customerId, int fundId, BigDecimal amount) throws Exception {
        try {
            Transaction.begin();
            if (fundDAO.read(fundId) == null) {
                throw new Exception("Fund does not exist!");
            }
            BigDecimal pendingAmount = transactionDAO.pendingAmount(customerId);
            Customer customer = customerDAO.read(customerId);
            BigDecimal availableCash = Customer.amountFromDouble(customer.getCash()).add(pendingAmount);
            if (amount.compareTo(availableCash) > 0) {
                throw new Exception("Not enough available cash!");
            }
            Transactions buyFund = new Transactions();
            buyFund.setStatus(Transactions.PENDING);
            buyFund.setType(Transactions.BUY);
            buyFund.setCustomerId(customer.getCustomerId());
            buyFund.setFundId(fundId);
            buyFund.setAmount(-amount.doubleValue());
            transactionDAO.create(buyFund);
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
