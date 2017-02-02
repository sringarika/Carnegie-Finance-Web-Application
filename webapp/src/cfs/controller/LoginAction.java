package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybeans.form.FormBeanFactory;

import cfs.databean.Customer;
import cfs.databean.Employee;
import cfs.formbean.LoginForm;
import cfs.model.CustomerDAO;
import cfs.model.EmployeeDAO;
import cfs.model.Model;

public class LoginAction extends Action {

    private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);

    private CustomerDAO customerDAO;

    private EmployeeDAO employeeDAO;

    public LoginAction(Model model) {
        customerDAO = model.getCustomerDAO();
        employeeDAO = model.getEmployeeDAO();
    }

    @Override
    public String getName() {
        return "login.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        // if GET request, return the login page
        if (request.getMethod().equals("GET")) {
            return "login.jsp";
        } else if (request.getMethod().equals("POST")) {
            HttpSession session = request.getSession();
            if (session.getAttribute("customerId") != null) {
                // if a customer already logged in, switch to the account page
                return "account.do";
            } else if (session.getAttribute("employeeId") != null) {
             // if employee already logged in, switch to the employee page
                return "employee.do";
            }
            try {
                LoginForm form = formBeanFactory.create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    request.setAttribute("error", validationErrors.get(0));
                    return "login.jsp";
                }
                // check for login validation
                Customer customer = customerDAO.findByUsername(form.getUsername());
                Employee employee = employeeDAO.findByUsername(form.getUsername());
                if (customer == null && employee == null) {
                    request.setAttribute("error", "The user doesn't exist.");
                    return "login.jsp";
                }
                if (customer != null) {
                    if (!customer.getPassword().equals(form.getPassword())) {
                        request.setAttribute("error", "Wrong password!");
                        return "login.jsp";
                    } else {
                        request.getSession().removeAttribute("employeeId");
                        request.getSession().setAttribute("customerId", customer.getCustomerId());
                        return "account.do";
                    }
                } else {
                    if (!employee.getPassword().equals(form.getPassword())) {
                        request.setAttribute("error", "Wrong password!");
                        return "login.jsp";
                    } else {
                        request.getSession().removeAttribute("customerId");
                        request.getSession().setAttribute("employeeId", employee.getEmployeeId());
                        return "employee.do";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", e.getMessage());
                return "error.jsp";
            }
        } else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Everyone;
    }
}
