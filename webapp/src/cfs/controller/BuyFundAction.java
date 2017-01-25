package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanFactory;

import cfs.formbean.BuyFundForm;
import cfs.model.Model;

public class BuyFundAction extends Action {

    public BuyFundAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "buy-fund.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            request.setAttribute("availableCash", 23.45);
            return "buy-fund.jsp";
        } else if (request.getMethod().equals("POST")) {
            try {
                BuyFundForm form = FormBeanFactory.getInstance(BuyFundForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    throw new Exception(validationErrors.get(0));
                }
                System.out.println("Buy Fund ID:" + form.getFundIdVal());
                System.out.println("Amount:" + form.getAmountVal());
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

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
