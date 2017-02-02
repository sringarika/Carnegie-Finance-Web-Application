package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanFactory;

import cfs.databean.Customer;
import cfs.formbean.ResetPasswordForm;
import cfs.model.CustomerDAO;
import cfs.model.Model;

public class ResetPasswordAction extends Action {

    private CustomerDAO customerdao;

    public ResetPasswordAction(Model model) {
        customerdao = model.getCustomerDAO();
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
            try {
                Customer customer = customerdao.read(customerId);
                String firstName = customer.getFirstname();
                String lastName = customer.getLastname();
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", e.getMessage());
                return "reset-password.jsp";
            }
            return "reset-password.jsp";
        } else if (request.getMethod().equals("POST")) {
            try {
                ResetPasswordForm form = FormBeanFactory.getInstance(ResetPasswordForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    request.setAttribute("error", validationErrors.get(0));
                    return "reset-password.jsp";
                }

                String newPsw = form.getNewPassword();
                customerdao.changePassword(customerId, newPsw);
                request.setAttribute("message", "Customer password reset successfully.");
                return "success.jsp";
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                return "error.jsp";
            }
        } else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}