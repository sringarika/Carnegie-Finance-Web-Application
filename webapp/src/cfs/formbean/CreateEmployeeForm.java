package cfs.formbean;
import java.util.ArrayList;
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
    public List<String> getValidationErrors() {
        //List<String> errors = new ArrayList<String>();
        System.out.println(password);
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
        //testing special characters
        if(username.contains("$")) {
            return Collections.singletonList("Username cannot contain special characters.");
            
        }
        if(firstname.contains("$")) {
            return Collections.singletonList("First Name cannot contain special characters.");
            
        }
        if(lastname.contains("$")) {
            return Collections.singletonList("Last Name cannot contain special characters.");
            
        }
        return Collections.emptyList();
}
}
