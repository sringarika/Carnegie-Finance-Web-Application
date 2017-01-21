package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanFactory;

import cfs.formbean.ChangePasswordForm;
import cfs.model.Model;

public class ChangePasswordAction extends Action {

    public ChangePasswordAction(Model model) {
        // TODO Auto-generated constructor stub
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
                    throw new Exception(validationErrors.get(0));
                }
                System.out.println("Old Password:" + form.getOldPassword());
                System.out.println("New Password:" + form.getNewPassword());
                System.out.println("Confirm Password:" + form.getConfirmPassword());
                // TODO
                request.setAttribute("message", "Password changed successfully!");
                return "success.jsp";
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                return "change-password.jsp";
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
