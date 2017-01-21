package cfs.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;

import cfs.databean.Customer;

public class CustomerDAO extends GenericDAO<Customer> {
    public CustomerDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(Customer.class, tableName, cp);
    }
}
