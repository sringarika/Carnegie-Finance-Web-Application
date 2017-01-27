package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.DuplicateKeyException;
import org.mybeans.form.FormBeanFactory;

import cfs.databean.Customer;
import cfs.databean.Employee;
import cfs.formbean.CreateEmployeeForm;
import cfs.model.EmployeeDAO;
import cfs.model.Model;

public class CreateEmployeeAction extends Action {
    private EmployeeDAO employeedao;
    public CreateEmployeeAction(Model model) {
        // TODO Auto-generated constructor stub
        employeedao= model.getEmployeeDAO();
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
                    throw new Exception(validationErrors.get(0));
                }
                Employee newEmployee = new Employee();
                newEmployee.setUsername(form.getUsername());
                newEmployee.setPassword(form.getNewPassword());
                newEmployee.setFirstname(form.getFirstName());
                newEmployee.setLastname(form.getLastName());
                //newEmployee.setEmployeeId(Integer.parseInt(form.getEmployeeID()));
                try {
                    employeedao.create(newEmployee);

                    //ServletRequest request;
                    //request.setAttribute("Customer", newCustomer);
                    
                } catch (DuplicateKeyException e) {
                    request.setAttribute("error", "A user with this username already exists!");
                    return "create-employee.jsp";
                }
//            System.out.println("First Name:" + form.getFirstName());
//            System.out.println("Last Name:" + form.getLastName());
//            System.out.println("Username:" + form.getUsername());
//            System.out.println("New Password:" + form.getNewPassword());
//            System.out.println("Confirm Password:" + form.getConfirmPassword());
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
