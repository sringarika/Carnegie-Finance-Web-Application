package cfs.formbean;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.mybeans.form.FormBean;

import cfs.databean.Customer;
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
    private String amount;
    private BigDecimal amountVal;
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
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getAmount() {
        return amount;
    }
    public BigDecimal getAmountVal() {
        return amountVal;
    }
    @Override
    public List<String> getValidationErrors() {
        if (password == null || password.isEmpty()) {
            return Collections.singletonList("Password is required!");
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

        if (!username.matches("[a-zA-Z0-9]+")) {
            return Collections.singletonList("Username can only contain letters and numbers!");
        }

        try {
            amountVal = Customer.amountFromStr(amount);
            if (amountVal.compareTo(BigDecimal.ZERO) < 0) {
                return Collections.singletonList("Amount must be non-negative");
            }
            if (amountVal.compareTo(new BigDecimal("1000000.00")) > 0) {
                return Collections.singletonList("Amount must not be more than $1,000,000.00!");
            }
        } catch(NumberFormatException num){
            return Collections.singletonList("Amount must be a valid number!");
        }
        catch (ArithmeticException e) {
            return Collections.singletonList("Amount can not be more than 2 decimal places!");
        } catch (Exception e) {
            return Collections.singletonList("Invalid amount!");
        }
        return Collections.emptyList();
    }
}