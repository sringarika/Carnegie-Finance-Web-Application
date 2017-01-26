package cfs.formbean;

import java.util.Collections;
import java.util.List;

import org.mybeans.form.FormBean;

public class ChangePasswordForm extends FormBean {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword.trim();
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword.trim();
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword.trim();
    }

    @Override
    public List<String> getValidationErrors() {
        if (oldPassword == null || oldPassword.isEmpty()) {
            return Collections.singletonList("Please enter your current password!");
        }
        if (newPassword == null || newPassword.isEmpty()) {
            return Collections.singletonList("Please enter your new password!");
        }
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            return Collections.singletonList("Please confirm your new password!!");
        }
        if (!newPassword.equals(confirmPassword)) {
            return Collections.singletonList("Confirm password does not match the new password you entered!");
        }
        return Collections.emptyList();
    }
}
