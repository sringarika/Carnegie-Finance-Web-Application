package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanFactory;

import cfs.databean.Customer;
import cfs.formbean.ChangePasswordForm;
import cfs.model.CustomerDAO;
import cfs.model.Model;
public class ResetPasswordAction extends Action {

    private CustomerDAO customerdao;
    public ResetPasswordAction(Model model) {
        // TODO Auto-generated constructor stub
        customerdao= model.getCustomerDAO();
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
            // TODO: Validate password & confirmPassword.
            // TODO: Use DAO to reset password for customerId.
            try {
                ChangePasswordForm form = FormBeanFactory.getInstance(ChangePasswordForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    throw new Exception(validationErrors.get(0));
                }
                System.out.println("New Password:" + form.getNewPassword());
                System.out.println("Confirm Password:" + form.getConfirmPassword());
                // TODO
            request.setAttribute("message", "Customer password reset successfully.");
            return "success.jsp";
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                return "reset-password.jsp";
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
