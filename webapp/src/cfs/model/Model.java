package cfs.model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import cfs.databean.Customer;
import cfs.databean.Employee;
import cfs.databean.Fund;
import cfs.databean.Position;

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
            if (customerDAO.findByUsername("bob") == null) {
                Customer customer = new Customer("bob", "Bob", "Brown", "1");
                customer.setAddrLine1("5000 Forbes Ave");
                customer.setCity("Pittsburgh");
                customer.setState("PA");
                customer.setZip("15213");
                customer.setCash(1000.00);
                customerDAO.create(customer);
                Fund fund = new Fund("Long-Term Treasury", "LTT");
                fundDAO.create(fund);
                Position position = new Position(customerDAO.findByUsername("bob").getCustomerId(),
                		fundDAO.findIdByName("Long-Term Treasury"), 1000.00);
                customerPositionDAO.create(position);
            }
            if (employeeDAO.findByUsername("admin") == null) {
                Employee employee = new Employee("admin", "Alice", "Admin", "1");
                employeeDAO.create(employee);
            }
        } catch (RollbackException e) {
            // Probably exists.
        }
    }
}
