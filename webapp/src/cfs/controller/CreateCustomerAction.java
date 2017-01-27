package cfs.controller;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.genericdao.DuplicateKeyException;
import org.mybeans.form.FormBeanFactory;

import cfs.databean.Customer;
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
                    request.setAttribute("error", validationErrors.get(0));
                    return "create-customer.jsp";
                }
                Customer newCustomer = new Customer();
                newCustomer.setUsername(form.getUsername());
                newCustomer.setPassword(form.getPassword());
                newCustomer.setFirstname(form.getFirstName());
                newCustomer.setLastname(form.getLastName());
                newCustomer.setAddrLine1(form.getAddress1());
                newCustomer.setAddrLine2(form.getAddress2());
                newCustomer.setCash(Double.parseDouble(form.getAmount()));
                newCustomer.setCity(form.getCity());
                newCustomer.setState(form.getState());
                newCustomer.setZip(form.getZipcode());
                //try {
                    customerdao.create(newCustomer);

                    //ServletRequest request;
                    //request.setAttribute("Customer", newCustomer);
                    
//                } catch (DuplicateKeyException e) {
//                    request.setAttribute("error", "A user with this username already exists!");
//                    return "create-customer.jsp";
//                }
               System.out.println("First Name:" + form.getFirstName());
//                System.out.println("Last Name:" + form.getLastName());
//                System.out.println("Username:" + form.getUsername());
//                System.out.println("Address Line 1:" + form.getAddress1());
//                System.out.println("Address Line 2:" + form.getAddress2());
//                System.out.println("Amount:" + form.getAmount());
//                System.out.println("New Password:" + form.getPassword());
//                System.out.println("Confirm Password:" + form.getConfirmPassword());
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
