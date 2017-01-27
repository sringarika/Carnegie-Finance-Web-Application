package cfs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanFactory;

import cfs.databean.Transactions;
import cfs.formbean.SellFundForm;
import cfs.model.CustomerPositionDAO;
import cfs.model.FundDAO;
import cfs.model.Model;
import cfs.model.TransactionDAO;
import cfs.viewbean.PositionView;

public class SellFundAction extends Action {

    private CustomerPositionDAO customerPositionDAO;
    private TransactionDAO transactionDAO;
    private FundDAO fundDAO;

    public SellFundAction(Model model) {
        customerPositionDAO = model.getCustomerPositionDAO();
        transactionDAO = model.getTransactionDAO();
        fundDAO = model.getFundDAO();
    }

    @Override
    public String getName() {
        return "sell-fund.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        int customerId = (int) request.getSession().getAttribute("customerId");
        try {
            PositionView[] positions = customerPositionDAO.getPositionViews(customerId);
            request.setAttribute("positions", positions);

            Map<Integer, PositionView> positionForFundId = new HashMap<Integer, PositionView>();
            for (int i = 0; i < positions.length; i++) {
                positionForFundId.put(positions[i].getFundId(), positions[i]);
            }

            Transactions[] transactions = transactionDAO.match(MatchArg.and(
                    MatchArg.equals("customerId", customerId),
                    MatchArg.equals("status", "Pending"),
                    MatchArg.equals("type", "Sell")));

            for (int i = 0; i < transactions.length; i++) {
                int fundId = transactions[i].getFundId();
                if (positionForFundId.containsKey(fundId)) {
                    PositionView position = positionForFundId.get(fundId);
                    position.setShares(position.getShares() + transactions[i].getShares());
                }
            }
        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }

        if (request.getMethod().equals("GET")) {
            return "sell-fund.jsp";
        } else if (request.getMethod().equals("POST")) {
            try {
                SellFundForm form = FormBeanFactory.getInstance(SellFundForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    throw new Exception(validationErrors.get(0));
                }
                queueTransaction(customerId, form.getFundIdVal(), form.getSharesVal());
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                return "sell-fund.jsp";
            }
            // TODO
            request.setAttribute("message", "Transaction scheduled. It will be processed by the end of the business day.");
            return "success.jsp";
        } else {
            return null;
        }
    }

    private void queueTransaction(int customerId, int fundId, double shares) throws Exception {
        try {
            Transaction.begin();
            if (fundDAO.read(fundId) == null) {
                throw new Exception("Fund does not exist!");
            }
            double existingShares = customerPositionDAO.existingShare(customerId, fundId);
            double pendingShares = transactionDAO.pendingShares(customerId, fundId);
            double availableShares = existingShares + pendingShares;
            if (shares > availableShares) {
                throw new Exception("Not enough available shares!");
            }
            Transactions sellFund = new Transactions();
            sellFund.setStatus("Pending");
            sellFund.setType("Sell");
            sellFund.setCustomerId(customerId);
            sellFund.setFundId(fundId);
            sellFund.setShares(-shares);
            transactionDAO.create(sellFund);
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
