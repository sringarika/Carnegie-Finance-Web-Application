package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.Transaction;
import org.mybeans.form.FormBeanFactory;

import cfs.databean.Employee;
import cfs.formbean.CreateEmployeeForm;
import cfs.model.CustomerDAO;
import cfs.model.EmployeeDAO;
import cfs.model.Model;

public class CreateEmployeeAction extends Action {
    private EmployeeDAO employeedao;
    private CustomerDAO customerdao;
    public CreateEmployeeAction(Model model) {
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
            try {
                CreateEmployeeForm form = FormBeanFactory.getInstance(CreateEmployeeForm.class).create(request);
                request.setAttribute("username", form.getUsername());
                request.setAttribute("firstname", form.getFirstname());
                request.setAttribute("lastname", form.getLastname());

                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    request.setAttribute("error", validationErrors.get(0));
                    return "create-employee.jsp";
                }
                Employee newEmployee = new Employee();
                try {
                    Transaction.begin();

                    newEmployee.setUsername(form.getUsername());
                    if (employeedao.findByUsername(form.getUsername()) != null
                            || customerdao.findByUsername(form.getUsername()) != null) {
                        request.setAttribute("error", "This username already exists!");
                        return "create-employee.jsp";
                    }
                    newEmployee.setPassword(form.getPassword());
                    newEmployee.setFirstname(form.getFirstname());
                    newEmployee.setLastname(form.getLastname());
                    employeedao.create(newEmployee);
                    Transaction.commit();
                } finally {
                    if (Transaction.isActive())
                        Transaction.rollback();
                }
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
