package cfs.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import cfs.databean.Employee;

public class EmployeeDAO extends GenericDAO<Employee> {

    public EmployeeDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Employee.class, tableName, cp);
    }

    /*
     * Check if employee exists
     */
    public Employee findByUsername(String username) throws RollbackException {
        Employee[] employee = match(MatchArg.equalsIgnoreCase("username", username));
        if (employee.length == 0) {
            return null;
        } else {
            return employee[0];
        }
    }

    /**
     * Change password for employee
     * @param username
     * @param password
     * @return the user
     * @throws RollbackException
     */
    public Employee changePassword(int employeeId, String password) throws RollbackException {
        try {
            Transaction.begin();
            Employee user = read(employeeId);
            if (user == null) {
                throw new RollbackException("Account for this user does not exist");
            }
            user.setPassword(password);
            super.update(user);
            Transaction.commit();
            return user;
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }

}