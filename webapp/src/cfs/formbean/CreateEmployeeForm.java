package cfs.formbean;
import java.util.Collections;
import java.util.List;

import org.mybeans.form.FormBean;
public class CreateEmployeeForm extends FormBean{
    private String password;
    private String confpassword;
    private String firstname;
    private String lastname;
    private String username;
    public String getPassword() {
        return password;
    }
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    public String getConfirmpassword() {
        return confpassword;
    }
    public void setConfirmpassword(String confirmPassword) {
        this.confpassword = confirmPassword;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastName) {
        this.lastname = lastName;
    }
    @Override
    public List<String> getValidationErrors() {
        if (password == null || password.isEmpty()) {
            return Collections.singletonList("New password is required!");
        }
        if (confpassword == null || confpassword.isEmpty()) {
            return Collections.singletonList("Confirm password is required!");
        }
        if (!password.equals(confpassword)) {
            return Collections.singletonList("Confirm password does not match new password!");
        }
        if (username == null || username.isEmpty()) {
            return Collections.singletonList("Username is required!");
        }
        if (firstname == null || firstname.isEmpty()) {
            return Collections.singletonList("First Name is required!");
        }
        if (lastname == null || lastname.isEmpty()) {
            return Collections.singletonList("Last Name is required!");
        }
        if (!username.matches("[a-zA-Z0-9]+")) {
            return Collections.singletonList("Username can only contain letters and numbers!");

        }
        return Collections.emptyList();
}
}
