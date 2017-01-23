package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.model.Model;

public class RequestCheckAction extends Action {

    public RequestCheckAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "request-check.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            // TODO: Get available cash.
            return "request-check.jsp";
        } else if (request.getMethod().equals("POST")) {
            // TODO
            request.setAttribute("message", "Check requested. It will be processed by the end of the business day.");
            return "success.jsp";
        } else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
