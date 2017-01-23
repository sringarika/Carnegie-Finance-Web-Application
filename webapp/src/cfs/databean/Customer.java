package cfs.databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("customerId")
public class Customer {
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
    
    // constructor is written as this for initial testing
    public Customer(int customerId, String username, String firstname, String lastname) {
        this.customerId = customerId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
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
