package cfs.formbean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.mybeans.form.FormBean;
public class CreateEmployee extends FormBean{
    private String newPassword;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String username;
    
    private String button;
    
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
    public String getLastname() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setButton(String button) {
        this.button = button;
    }

    public String getButton() {
        return button;
    }

    public boolean isPresent() {        
        if(button == null) return false;
        return true;
    }

    public List<String> getValidationErrors() {
        //List<String> errors = new ArrayList<String>();
        if (newPassword == null || newPassword.isEmpty()) {
            return Collections.singletonList("New password is required!");
        }
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            return Collections.singletonList("Confirm password is required!");
        }
        if (!newPassword.equals(confirmPassword)) {
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
        

//        if (errors.size() > 0)
//            return errors;

        //return errors;
        return Collections.emptyList();
}
}
