package cfs.databean;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.genericdao.PrimaryKey;

@PrimaryKey("customerId")
public class Customer {

    public static BigDecimal amountFromStr(String shares) {
        // Use UNNECESSARY here to assert that the input string must not have more than 2 decimals.
        return new BigDecimal(shares).setScale(2, RoundingMode.UNNECESSARY);
    }

    public static BigDecimal amountFromDouble(double shares) {
        // Round the double value half because floating point error can be either positive or negative.
        // For example, 2.3300000000007 and 2.3299999999999 should both be considered 2.33.
        return BigDecimal.valueOf(shares).setScale(2, RoundingMode.HALF_EVEN);
    }

    private int customerId;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String addrLine1;
    private String addrLine2;
    private String city;
    private String state;
    private String zip;
    private double cash;

    public Customer() {
    }

    // constructor for creating a customer, the rest fields are optional
    public Customer(String username, String firstname, String lastname, String password) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddrLine1() {
        return addrLine1;
    }
    public void setAddrLine1(String addrLine1) {
        this.addrLine1 = addrLine1;
    }

    public String getAddrLine2() {
        return addrLine2;
    }
    public void setAddrLine2(String addrLine2) {
        this.addrLine2 = addrLine2;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }

    public double getCash() {
        return cash;
    }
    public void setCash(double cash) {
        this.cash = cash;
    }
}
