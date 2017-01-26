package cfs.controller;

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
            double pendingAmount = transactionDAO.pendingAmount(customer.getCustomerId());
            request.setAttribute("availableCash", customer.getCash() + pendingAmount);
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
            // TODO
            request.setAttribute("message", "Transaction scheduled. It will be processed by the end of the business day.");
            return "success.jsp";
        } else {
            return null;
        }
    }

    private void queueTransaction(int customerId, int fundId, double amount) throws Exception {
        try {
            Transaction.begin();
            if (fundDAO.read(fundId) == null) {
                throw new Exception("Fund does not exist!");
            }
            double pendingAmount = transactionDAO.pendingAmount(customerId);
            Customer customer = customerDAO.read(customerId);
            double availableCash = customer.getCash() + pendingAmount;
            if (amount > availableCash) {
                throw new Exception("Not enough available cash!");
            }
            Transactions buyFund = new Transactions();
            buyFund.setStatus("Pending");
            buyFund.setType("Buy");
            buyFund.setCustomerId(customer.getCustomerId());
            buyFund.setFundId(fundId);
            buyFund.setAmount(-amount);
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
