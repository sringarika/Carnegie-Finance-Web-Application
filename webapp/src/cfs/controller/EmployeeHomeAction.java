package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.model.Model;

public class EmployeeHomeAction extends Action {

    public EmployeeHomeAction(Model model) {
    }

    @Override
    public String getName() {
        return "employee.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        return "employee-home.jsp";
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
