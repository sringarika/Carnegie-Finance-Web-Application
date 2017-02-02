package cfs.databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("employeeId")
public class Employee {
    private int employeeId;
    private String username;
    private String password;
    private String firstname;
    private String lastname;

    public Employee() {
    }

    public Employee(String username, String firstname, String lastname, String password) {
        this.password = password;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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
}
