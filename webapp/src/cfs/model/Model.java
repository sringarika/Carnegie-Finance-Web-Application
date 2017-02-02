package cfs.model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import cfs.databean.Employee;
import cfs.databean.Fund;

public class Model {
    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;
    private CustomerPositionDAO customerPositionDAO;
    private FundPriceHistoryDAO fundPriceHistoryDAO;
    private FundDAO fundDAO;
    private EmployeeDAO employeeDAO;
    private TransactionProcessor transactionProcessor;

    public Model(ServletConfig config) throws ServletException {
        try {
            String jdbcDriver = config.getInitParameter("jdbcDriverName");
            String jdbcURL    = config.getInitParameter("jdbcURL");

            ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL);
            pool.setDebugOutput(System.out);

            customerDAO = new CustomerDAO(pool, "customer");
            transactionDAO = new TransactionDAO(pool, "transactions");
            customerPositionDAO = new CustomerPositionDAO(pool, "position");
            fundPriceHistoryDAO = new FundPriceHistoryDAO(pool, "fundprice");
            fundDAO = new FundDAO(pool, "fund");
            employeeDAO = new EmployeeDAO(pool, "employee");
            transactionProcessor = new TransactionProcessor(this, pool);
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

    public TransactionProcessor getTransactionProcessor() {
        return transactionProcessor;
    }

    public void seed() {
        try {
            if (employeeDAO.findByUsername("admin") == null) {
                Employee employee = new Employee("admin", "Alice", "Admin",
                        "teambinary");
                employeeDAO.create(employee);
            }
            if (!fundDAO.fundTicker("LTT")) {
                Fund fund1 = new Fund("Long-Term Treasury", "LTT");
                fundDAO.create(fund1);
            }
            if (!fundDAO.fundTicker("CMU")) {
                Fund fund2 = new Fund("Carnegie Mellon U", "CMU");
                fundDAO.create(fund2);
            }
        } catch (RollbackException e) {
            e.printStackTrace();
        }
    }

}
