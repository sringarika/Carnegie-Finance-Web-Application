package cfs.controller;

import javax.servlet.http.HttpServletRequest;

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
            if (!request.getParameter("password").equals("secret")) {
                request.setAttribute("error", "Wrong password!");
                return "login.jsp";
            }
            if (request.getParameter("username").equals("admin")) {
                request.getSession().removeAttribute("customerId");
                request.getSession().setAttribute("employeeId", 1);
                return "employee.do";
            } else if (request.getParameter("username").equals("carl")) {
                request.getSession().removeAttribute("employeeId");
                request.getSession().setAttribute("customerId", 2);
                return "account.do";
            } else {
                request.setAttribute("error", "User does not exist!");
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
