package cfs.controller;

import javax.servlet.http.HttpServletRequest;

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
