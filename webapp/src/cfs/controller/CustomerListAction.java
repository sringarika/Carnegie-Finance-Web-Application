package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import cfs.databean.Customer;
import cfs.model.CustomerDAO;
import cfs.model.Model;

public class CustomerListAction extends Action {

    private CustomerDAO customerDAO;

    public CustomerListAction(Model model) {
        customerDAO = model.getCustomerDAO();
    }

    @Override
    public String getName() {
        return "customer-list.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        Customer[] customers;
        try {
            customers = customerDAO.match();
        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
        request.setAttribute("customers", customers);
        return "customer-list.jsp";
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
