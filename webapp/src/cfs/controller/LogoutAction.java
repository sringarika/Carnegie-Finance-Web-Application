package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.model.Model;

public class LogoutAction extends Action {

    public LogoutAction(Model model) {
    }

    @Override
    public String getName() {
        return "logout.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        request.getSession().removeAttribute("customerId");
        request.getSession().removeAttribute("employeeId");
        return "login.do";
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Everyone;
    }
}
