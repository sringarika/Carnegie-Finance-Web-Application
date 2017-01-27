package cfs.formbean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.mybeans.form.FormBean;
public class CreateCustomerForm extends FormBean {
    private String password;
    private String confPassword;
    private String firstName;
    private String lastName;
    private String username;
    private String address1;
    private String address2;
    private String amount;
    //private String button;
    
    public String getPassword() {
        return password;
    }
    public void setNewPassword(String newPassword) {
        this.password = newPassword;
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
    public String getAddress1() {
        return address1;
    }
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    public String getAddress2() {
        return address2;
    }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getAmount() {
        System.out.println(amount);
        return amount;
    }
    public List<String> getValidationErrors() {
        //List<String> errors = new ArrayList<String>();
        if (password == null || password.isEmpty()) {
            return Collections.singletonList("Password is required!");
        }
        if (confPassword == null || confPassword.isEmpty()) {
            return Collections.singletonList("Confirm password is required!");
        }
        if (!password.equals(confPassword)) {
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
        if (address1 == null || address1.isEmpty()) {
            return Collections.singletonList("Current address is required!");
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
        if(amount.contains("$")) {
            return Collections.singletonList("Amount cannot contain special characters.");
            
        }

        if (amount == null || amount.trim().length() == 0)
            return Collections.singletonList("Amount is required");
        
        double a;
        try {
            a = Double.parseDouble(amount);
            if(a < 0.00) {
                return Collections.singletonList("Amount must atleast be $0.00!");
            }
            System.out.println(a);
            if(a > 1000000.00) {
                return Collections.singletonList("Amount cannot be greater than $1,000,000.00!");
            }
        } catch(NumberFormatException num){
            return Collections.singletonList("Amount must be a valid number!");
        }
        return Collections.emptyList();
    }
}
    

