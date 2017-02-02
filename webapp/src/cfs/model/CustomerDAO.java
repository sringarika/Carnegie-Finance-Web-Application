package cfs.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import cfs.databean.Customer;

public class CustomerDAO extends GenericDAO<Customer> {
    public CustomerDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Customer.class, tableName, cp);
    }

    public Customer findByUsername(String username) throws RollbackException {
        Customer[] customer = match(MatchArg.equalsIgnoreCase("username", username));
        if (customer.length == 0) {
            return null;
        } else {
            return customer[0];
        }
    }

    public Customer changePassword(int customerId, String password) throws RollbackException {
        try {
            Transaction.begin();
            Customer user = read(customerId);
            if (user == null) {
                throw new RollbackException("Account for this user no longer exists");
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
