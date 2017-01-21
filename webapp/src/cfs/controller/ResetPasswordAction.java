package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.model.Model;

public class ResetPasswordAction extends Action {

    public ResetPasswordAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "reset-password.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        int customerId;
        String customerIdStr = request.getParameter("customerId");
        try {
            customerId = Integer.parseInt(customerIdStr);
        } catch (Exception e) {
            request.setAttribute("error", "Invalid customerId!");
            return "error.jsp";
        }
        request.setAttribute("customerId", customerId);
        
        if (request.getMethod().equals("GET")) {
            // TODO: Maybe get some information (e.g. name) for display?
            return "reset-password.jsp";
        } else if (request.getMethod().equals("POST")) {
            // TODO: Validate password & confirmPassword.
            // TODO: Use DAO to reset password for customerId.
            request.setAttribute("message", "Customer password reset successfully.");
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
