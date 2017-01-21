package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.model.Model;

public class EmployeeHomeAction extends Action {

    public EmployeeHomeAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "employee.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        // TODO Get account information using DAO.
        return "employee-home.jsp";
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
