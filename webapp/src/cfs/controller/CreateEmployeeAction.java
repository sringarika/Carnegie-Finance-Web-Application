package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.DuplicateKeyException;
import org.mybeans.form.FormBeanFactory;

import cfs.databean.Customer;
import cfs.databean.Employee;
import cfs.formbean.CreateEmployeeForm;
import cfs.model.CustomerDAO;
import cfs.model.EmployeeDAO;
import cfs.model.Model;

public class CreateEmployeeAction extends Action {
    private EmployeeDAO employeedao;
    private CustomerDAO customerdao;
    public CreateEmployeeAction(Model model) {
        // TODO Auto-generated constructor stub
        employeedao= model.getEmployeeDAO();
        customerdao= model.getCustomerDAO();
    }

    @Override
    public String getName() {
        return "create-employee.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return "create-employee.jsp";
        } else if (request.getMethod().equals("POST")) {
            // TODO
            try {
                CreateEmployeeForm form = FormBeanFactory.getInstance(CreateEmployeeForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    request.setAttribute("error", validationErrors.get(0));
                    return "create-employee.jsp";
                }
                Employee newEmployee = new Employee();
                newEmployee.setUsername(form.getUsername());
                if (employeedao.findByUsername(form.getUsername()) != null || customerdao.findByUsername(form.getUsername()) != null) {
                    request.setAttribute("error", "This username already exists!");
                    return "create-employee.jsp";
                }
                newEmployee.setPassword(form.getPassword());
                newEmployee.setFirstname(form.getFirstname());
                newEmployee.setLastname(form.getLastname());
                //newEmployee.setEmployeeId(Integer.parseInt(form.getEmployeeID()));
                //try {
                    employeedao.create(newEmployee);

            request.setAttribute("message", "Employee created successfully!");
            return "success.jsp";
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                return "create-employee.jsp";
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
