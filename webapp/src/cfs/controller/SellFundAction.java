package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanFactory;

import cfs.formbean.SellFundForm;
import cfs.model.Model;
import cfs.viewbean.CustomerFundView;

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
            // TODO: Use DAO to get the view beans.
            CustomerFundView[] cusFunds = {
                    new CustomerFundView(1, "Long-Term Treasury", "LTT", 1000.000, 11.88),
                    new CustomerFundView(2, "Index Admiral Shares", "IAS", 1000.000, 209.79),
                    new CustomerFundView(3, "Strategic Equity", "SE", 5000.000, 32.88),
            };
            request.setAttribute("cusFunds", cusFunds);
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
