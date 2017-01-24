package cfs.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import cfs.databean.Customer;

public class CustomerDAO extends GenericDAO<Customer> {
    public CustomerDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Customer.class, tableName, cp);
    }
    
    public Customer changePassword(String username, String password) throws RollbackException {
        try {
            Transaction.begin();
            Customer user = read(username);
            if (user == null) {
                throw new RollbackException("Account for" + username + " no longer exists");
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
