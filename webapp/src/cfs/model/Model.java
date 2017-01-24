package cfs.model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import cfs.databean.Customer;
import cfs.databean.Employee;

public class Model {
    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;
    private CustomerPositionDAO customerPositionDAO;
    private FundPriceHistoryDAO fundPriceHistoryDAO;
    private FundDAO fundDAO;
    private EmployeeDAO employeeDAO;

    public Model(ServletConfig config) throws ServletException {
        try {
            String jdbcDriver = config.getInitParameter("jdbcDriverName");
            String jdbcURL    = config.getInitParameter("jdbcURL");

            ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL);

            customerDAO = new CustomerDAO(pool, "customer");
            transactionDAO = new TransactionDAO(pool, "transactions");
            customerPositionDAO = new CustomerPositionDAO(pool, "position");
            fundPriceHistoryDAO = new FundPriceHistoryDAO(pool, "fundprice");
            fundDAO = new FundDAO(pool, "fund");
            employeeDAO = new EmployeeDAO(pool, "employee");
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

    public FundDAO getFundDAO() {
        return fundDAO;
    }

    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }

    public void seed() {
        try {
            if (customerDAO.findByUsername("carl") == null) {
                Customer customer = new Customer();
                customer.setUsername("carl");
                customer.setFirstname("Carl");
                customer.setLastname("Customer");
                customer.setPassword("secret");
                customerDAO.create(customer);
            }
            if (employeeDAO.findByUsername("admin") == null) {
                Employee employee = new Employee();
                employee.setUsername("admin");
                employee.setFirstname("Alice");
                employee.setLastname("Admin");
                employee.setPassword("secret");
                employeeDAO.create(employee);
            }
        } catch (RollbackException e) {
            // Probably exists.
        }
    }
}
