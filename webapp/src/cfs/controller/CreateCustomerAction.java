package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanFactory;

import cfs.formbean.CreateCustomerForm;
import cfs.formbean.ResetPasswordForm;
import cfs.model.CustomerDAO;
import cfs.model.Model;

public class CreateCustomerAction extends Action {
    private CustomerDAO customerdao;

    public CreateCustomerAction(Model model) {
        // TODO Auto-generated constructor stub
        customerdao= model.getCustomerDAO();
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
            try {
                CreateCustomerForm form = FormBeanFactory.getInstance(CreateCustomerForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    throw new Exception(validationErrors.get(0));
                }
                System.out.println("First Name:" + form.getFirstName());
                System.out.println("Last Name:" + form.getLastName());
                System.out.println("Username:" + form.getUsername());
                System.out.println("Address Line 1:" + form.getAddress1());
                System.out.println("Address Line 2:" + form.getAddress2());
                System.out.println("Amount:" + form.getAmount());
                System.out.println("New Password:" + form.getPassword());
                System.out.println("Confirm Password:" + form.getConfirmPassword());
                // TODO
            request.setAttribute("message", "Customer created successfully!");
            return "success.jsp";
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                return "create-customer.jsp";
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
