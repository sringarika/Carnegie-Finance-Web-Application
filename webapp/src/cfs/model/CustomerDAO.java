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
    	Customer[] customer = match(MatchArg.equals("username", username));
		if (customer.length == 0) {
			return null;
		} else {
			return customer[0];
		}
    }
    
    public void increaseCash(double cash, int customerId) throws RollbackException {
        try {
            Transaction.begin();
            Customer customer = read(customerId);
            if (customer == null) throw new RollbackException ("User doesn't exist!");
            double availableCash = customer.getCash() + cash;
            customer.setCash(availableCash);
            update(customer);
            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        } 
    }
    
    public void deductCash(double cash, int customerId) throws RollbackException {
        try {
            Transaction.begin();
            Customer customer = read(customerId);
            if (customer == null) throw new RollbackException ("User doesn't exist!");
            double availableCash = customer.getCash() + cash;
            customer.setCash(availableCash);
            update(customer);
            Transaction.commit();
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        } 
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
