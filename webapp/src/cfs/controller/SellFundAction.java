package cfs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanFactory;

import cfs.formbean.SellFundForm;
import cfs.model.CustomerPositionDAO;
import cfs.model.Model;
import cfs.viewbean.PositionView;

public class SellFundAction extends Action {

    private CustomerPositionDAO customerPositionDAO;

    public SellFundAction(Model model) {
        customerPositionDAO = model.getCustomerPositionDAO();
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
            // TODO: Get availableSharesForFund from DAO.
            Map<Integer, Double> availableSharesForFund = new HashMap<Integer, Double>();
            for (int i = 0; i < positions.length; i++) {
                availableSharesForFund.put(positions[i].getFundId(), positions[i].getShares());
            }
            request.setAttribute("availableSharesForFund", availableSharesForFund);
        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
        // TODO: Get the available shares for each fund.

        if (request.getMethod().equals("GET")) {
            return "sell-fund.jsp";
        } else if (request.getMethod().equals("POST")) {
            try {
                SellFundForm form = FormBeanFactory.getInstance(SellFundForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    throw new Exception(validationErrors.get(0));
                }
                System.out.println("Sell fund ID:" + form.getFundIdVal());
                System.out.println("Shares:" + form.getSharesVal());
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

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
