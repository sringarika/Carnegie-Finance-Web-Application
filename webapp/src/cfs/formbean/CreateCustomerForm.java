package cfs.formbean;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.mybeans.form.FormBean;
public class CreateCustomerForm extends FormBean {
    private String password;
    private String confpassword;
    private String firstName;
    private String lastName;
    private String username;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipcode;
    private BigDecimal amount;
    public String getPassword() {
        return password;
    }
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    public String getConfpassword() {
        return confpassword;
    }
    public void setConfpassword(String confirmPassword) {
        this.confpassword = confirmPassword;
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
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public BigDecimal getAmount() {
        System.out.println(amount);
        return amount;
    }
    public List<String> getValidationErrors() {
        if (password == null || password.isEmpty()) {
            return Collections.singletonList("Password is required!");
        }
    	System.out.println("Password is "+password);
        if (confpassword == null || confpassword.isEmpty()) {
            return Collections.singletonList("Confirm password is required!");
        }
        if (!password.equals(confpassword)) {
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
        if (state == null || state.isEmpty()) {
            return Collections.singletonList("State is required!");
        }
        if (city == null || city.isEmpty()) {
            return Collections.singletonList("City is required!");
        }
        if (zipcode == null || zipcode.isEmpty()) {
            return Collections.singletonList("Zipcode is required!");
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
        //double a;
        try {
           // a = Double.parseDouble(amount);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                return Collections.singletonList("Amount must be positive!");
            }
            if (amount.compareTo(new BigDecimal("1000000.00")) > 0) {
                return Collections.singletonList("Amount must not be more than $1,000,000.00!");
            }
        } catch(NumberFormatException num){
            return Collections.singletonList("Amount must be a valid number!");
        }
        return Collections.emptyList();
    }
}