package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanFactory;

import cfs.formbean.LoginForm;
import cfs.model.Model;

public class LoginAction extends Action {

    public LoginAction(Model model) {
        // TODO Auto-generated constructor stub
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
            // TODO: Use DAO to login.
            try {
                LoginForm form = FormBeanFactory.getInstance(LoginForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    throw new Exception(validationErrors.get(0));
                }
              //  System.out.println("UserName:" + form.getUsername());
               // System.out.println("Password:" + form.getPassword());
                
                if(!form.getPassword().equals("secret")) {
                	request.setAttribute("error", "Wrong password!");
                    return "login.jsp";
                }
                
                if(form.getUsername().equals("admin")) {
                	request.getSession().removeAttribute("customerId");
                    request.getSession().setAttribute("employeeId", 1);
                    return "employee.do";
                }else if(form.getUsername().equals("carl")) {
                	request.getSession().removeAttribute("employeeId");
                    request.getSession().setAttribute("customerId", 2);
                    return "account.do";
                } else {
                	request.setAttribute("error", "User does not exist!");
                    return "login.jsp";
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
