package cfs.model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import cfs.databean.Customer;

public class Model {
    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;
    private CustomerPositionDAO customerPositionDAO;
    private FundPriceHistoryDAO fundPriceHistoryDAO;

    public Model(ServletConfig config) throws ServletException {
        try {
            String jdbcDriver = config.getInitParameter("jdbcDriverName");
            String jdbcURL    = config.getInitParameter("jdbcURL");
            
            ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL);
            
            customerDAO = new CustomerDAO(pool, "customer");
            transactionDAO = new TransactionDAO(pool, "transactions");
            customerPositionDAO = new CustomerPositionDAO(pool, "Customer's Positions");
            fundPriceHistoryDAO = new FundPriceHistoryDAO(pool, "Fund Price History");
        } catch (DAOException e) {
            throw new ServletException(e);
        }
    }

    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }
    
    public TransactionDAO getTransactionDAO() {
        return transactionDAO;
    }
    
    public CustomerPositionDAO getCustomerPositionDAO() {
        return customerPositionDAO;
    }
    
    public FundPriceHistoryDAO getFundPriceHistoryDAO() {
        return fundPriceHistoryDAO;
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
