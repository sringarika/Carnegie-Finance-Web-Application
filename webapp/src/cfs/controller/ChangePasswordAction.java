package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanFactory;

import cfs.formbean.ChangePasswordForm;
import cfs.model.CustomerDAO;
import cfs.model.EmployeeDAO;
import cfs.model.Model;

public class ChangePasswordAction extends Action {

    private CustomerDAO customerDAO;

    private EmployeeDAO employeeDAO;

    public ChangePasswordAction(Model model) {
        customerDAO = model.getCustomerDAO();
        employeeDAO = model.getEmployeeDAO();
    }

    @Override
    public String getName() {
        return "change-password.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return "change-password.jsp";
        } else if (request.getMethod().equals("POST")) {
            try {
                ChangePasswordForm form = FormBeanFactory.getInstance(ChangePasswordForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    request.setAttribute("error", validationErrors.get(0));
                    return "change-password.jsp";
                }
                String oldPsw = form.getOldPassword();
                String newPsw = form.getNewPassword();
                if (request.getSession().getAttribute("customerId") != null) {
                    int customerId = (int) request.getSession().getAttribute("customerId");
                    if (!customerDAO.read(customerId).getPassword().equals(oldPsw)) {
                        request.setAttribute("error", "The current password you entered is wrong!");
                        return "change-password.jsp";
                    } else {
                        customerDAO.changePassword(customerId, newPsw);
                        request.setAttribute("message", "Password changed successfully!");
                        return "success.jsp";
                    }
                } else if (request.getSession().getAttribute("employeeId") != null) {
                    int employeeId = (int) request.getSession().getAttribute("employeeId");
                    if (!employeeDAO.read(employeeId).getPassword().equals(oldPsw)) {
                        request.setAttribute("error", "The current password you entered is wrong!");
                        return "change-password.jsp";
                    } else {
                        employeeDAO.changePassword(employeeId, newPsw);
                        request.setAttribute("message", "Password changed successfully!");
                        return "success.jsp";
                    }
                } else {
                    return "login.do";
                }
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                return "error.jsp";
            }
        } else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.User;
    }
}
