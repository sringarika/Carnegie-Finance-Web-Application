package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.model.Model;

public class CreateCustomerAction extends Action {

    public CreateCustomerAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "create-customer.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return "create-customer.jsp";
        } else if (request.getMethod().equals("POST")) {
            // TODO
            request.setAttribute("message", "Customer created successfully!");
            return "success.jsp";
        } else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
