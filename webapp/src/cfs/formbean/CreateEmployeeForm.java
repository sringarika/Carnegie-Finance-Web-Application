package cfs.formbean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.mybeans.form.FormBean;
public class CreateEmployeeForm extends FormBean{
    private String newPassword;
    private String confPassword;
    private String firstName;
    private String lastName;
    private String username;
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getConfirmPassword() {
        return confPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confPassword = confirmPassword;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public List<String> getValidationErrors() {
        //List<String> errors = new ArrayList<String>();
        if (newPassword == null || newPassword.isEmpty()) {
            return Collections.singletonList("New password is required!");
        }
        if (confPassword == null || confPassword.isEmpty()) {
            return Collections.singletonList("Confirm password is required!");
        }
        if (!newPassword.equals(confPassword)) {
            return Collections.singletonList("Confirm password does not match new password!");
        }
        if (username == null || username.isEmpty()) {
            return Collections.singletonList("Username is required!");
        }
        if (firstName == null || firstName.isEmpty()) {
            return Collections.singletonList("First Name is required!");
        }
        if (lastName == null || lastName.isEmpty()) {
            return Collections.singletonList("Last Name is required!");
        }
        //testing special characters
        if(username.contains("$")) {
            return Collections.singletonList("Username cannot contain special characters.");
            
        }
        if(firstName.contains("$")) {
            return Collections.singletonList("First Name cannot contain special characters.");
            
        }
        if(lastName.contains("$")) {
            return Collections.singletonList("Last Name cannot contain special characters.");
            
        }
        return Collections.emptyList();
}
}
