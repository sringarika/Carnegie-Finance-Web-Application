package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.model.Model;

public class AccountAction extends Action {

    public AccountAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "account.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        return "account.jsp";
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
