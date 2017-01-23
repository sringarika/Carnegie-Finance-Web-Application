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
        this.oldPassword = oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public List<String> getValidationErrors() {
        if (oldPassword == null || oldPassword.isEmpty()) {
            return Collections.singletonList("Current password is required!");
        }
        if (newPassword == null || newPassword.isEmpty()) {
            return Collections.singletonList("New password is required!");
        }
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            return Collections.singletonList("Confirm password is required!");
        }
        if (!newPassword.equals(confirmPassword)) {
            return Collections.singletonList("Confirm password does not match new password!");
        }
        return Collections.emptyList();
    }
}
