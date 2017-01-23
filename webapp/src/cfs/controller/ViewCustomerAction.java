package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.model.Model;

public class ViewCustomerAction extends Action {

    public ViewCustomerAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "view-customer.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        return "view-customer.jsp";
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
