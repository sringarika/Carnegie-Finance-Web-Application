package cfs.model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import cfs.databean.Customer;

public class Model {
    private CustomerDAO customerDAO;

    public Model(ServletConfig config) throws ServletException {
        try {
            String jdbcDriver = config.getInitParameter("jdbcDriverName");
            String jdbcURL    = config.getInitParameter("jdbcURL");
            
            ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL);
            
            customerDAO = new CustomerDAO(pool, "customer");
        } catch (DAOException e) {
            throw new ServletException(e);
        }
    }

    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }
    
    public void seed() {
        try {
            Customer customer = new Customer(0, "carl@example.com", "Carl", "Customer");
            customer.setPassword("secret");
            customerDAO.create(customer);
        } catch (RollbackException e) {
            // Probably exists.
        }
    }
}
