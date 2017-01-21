package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.model.Model;

public class DepositCheckAction extends Action {

    public DepositCheckAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "deposit-check.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            // TODO: Maybe fetch the customer list for reference?
            return "deposit-check.jsp";
        } else if (request.getMethod().equals("POST")) {
            // TODO
            request.setAttribute("message", "Check deposited successfully! The transaction will be processed by the end of the business day.");
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
