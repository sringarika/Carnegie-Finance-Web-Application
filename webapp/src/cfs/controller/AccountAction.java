package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.model.Model;

public class AccountAction extends ViewCustomerAction {

    public AccountAction(Model model) {
        super(model);
    }

    @Override
    public String getName() {
        return "account.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        int customerId = (int) request.getSession().getAttribute("customerId");
        request.setAttribute("isMyAccount", true);
        return super.showCustomer(customerId, request);
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
