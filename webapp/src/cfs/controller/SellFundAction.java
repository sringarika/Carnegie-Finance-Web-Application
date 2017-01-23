package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanFactory;

import cfs.formbean.SellFundForm;
import cfs.model.Model;

public class SellFundAction extends Action {

    public SellFundAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "sell-fund.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            // TODO: Get the list of positions (with fund and shares).
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
