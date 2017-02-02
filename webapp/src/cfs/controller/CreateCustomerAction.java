package cfs.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.Transaction;
import org.mybeans.form.FormBeanFactory;

import cfs.databean.Customer;
import cfs.databean.Transactions;
import cfs.formbean.CreateCustomerForm;
import cfs.model.CustomerDAO;
import cfs.model.EmployeeDAO;
import cfs.model.Model;
import cfs.model.TransactionDAO;


public class CreateCustomerAction extends Action {
    private CustomerDAO customerdao;
    private EmployeeDAO employeedao;
    private TransactionDAO transactiondao;

    public CreateCustomerAction(Model model) {
        customerdao= model.getCustomerDAO();
        employeedao= model.getEmployeeDAO();
        transactiondao = model.getTransactionDAO();
    }

    @Override
    public String getName() {
        return "create-customer.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            return "create-customer.jsp";
        } else if (request.getMethod().equals("POST")) {
            try {
                CreateCustomerForm form = FormBeanFactory.getInstance(CreateCustomerForm.class).create(request);
                request.setAttribute("username", form.getUsername());
                request.setAttribute("address1", form.getAddress1());
                request.setAttribute("address2", form.getAddress2());
                request.setAttribute("amount", form.getAmount());
                request.setAttribute("city", form.getCity());
                request.setAttribute("state", form.getState());
                request.setAttribute("zipcode", form.getZipcode());
                request.setAttribute("firstName", form.getFirstName());
                request.setAttribute("lastName", form.getLastName());

                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    request.setAttribute("error", validationErrors.get(0));
                    return "create-customer.jsp";
                }
                Customer newCustomer = new Customer();
                try {
                    Transaction.begin();
                    newCustomer.setUsername(form.getUsername());
                    if (customerdao.findByUsername(form.getUsername()) != null
                            || employeedao.findByUsername(form.getUsername()) != null) {
                        request.setAttribute("error", "This username already exists!");
                        return "create-customer.jsp";
                    }
                    newCustomer.setPassword(form.getPassword());
                    newCustomer.setFirstname(form.getFirstName());
                    newCustomer.setLastname(form.getLastName());
                    newCustomer.setAddrLine1(form.getAddress1());
                    newCustomer.setAddrLine2(form.getAddress2());
                    newCustomer.setCity(form.getCity());
                    newCustomer.setState(form.getState());
                    newCustomer.setZip(form.getZipcode());
                    customerdao.create(newCustomer);
                    // deposit initial amount
                    if (form.getAmountVal().compareTo(BigDecimal.ZERO) > 0) {
                        Transactions transaction = new Transactions();
                        transaction.setCustomerId(newCustomer.getCustomerId());
                        transaction.setType(Transactions.DEPOSIT_CHECK);
                        transaction.setAmount(form.getAmountVal().doubleValue());
                        transaction.setStatus(Transactions.PENDING);
                        transactiondao.create(transaction);
                    }
                    Transaction.commit();
                } finally {
                    if (Transaction.isActive())
                        Transaction.rollback();
                }

                request.setAttribute("message", "Customer created successfully!");
                return "success.jsp";
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                return "create-customer.jsp";
            }

        } else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}