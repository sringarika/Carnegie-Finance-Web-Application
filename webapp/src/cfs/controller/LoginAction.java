package cfs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybeans.form.FormBeanFactory;

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
        if (request.getMethod().equals("GET")) {
            return "login.jsp";
        } else if (request.getMethod().equals("POST")) {
        	HttpSession session = request.getSession();
        	// if someone already logged in, switch to the account page
            if (session.getAttribute("customer") != null) {
        		return "account.do";
        	} else if (session.getAttribute("employee") != null) {
        		return "employee.do";
        	}
            // check for any input errors
            ArrayList<String> errors = new ArrayList<String>();
            request.setAttribute("errors", errors);
            try {
                LoginForm form = formBeanFactory.create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                	errors.addAll(validationErrors);
                	return "login.jsp";
                }
                // check for login validation
                if (customerDAO.read(form.getUsername()) == null && employeeDAO.read(form.getUsername()) == null) {
                	errors.add("The user doesn't exist.");
                    return "login.jsp";
                }
                if (customerDAO.read(form.getUsername()) != null) {
                	if (customerDAO.read(form.getUsername()).getPassword() != form.getPassword()) {
                		request.setAttribute("error", "Wrong password!");
                        return "login.jsp";
                	} else {
                        request.getSession().setAttribute("customer", customerDAO.read(form.getUsername()));
                        return "account.do";
                	}
                } else {
                	if (employeeDAO.read(form.getUsername()).getPassword() != form.getPassword()) {
                		request.setAttribute("error", "Wrong password!");
                        return "login.jsp";
                	} else {
                        request.getSession().setAttribute("employee", employeeDAO.read(form.getUsername()));
                        return "employee.do";
                	}
                }
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                return "login.jsp";
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
