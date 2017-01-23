package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.databean.Customer;
import cfs.model.Model;

public class CustomerListAction extends Action {

    public CustomerListAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "customer-list.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        // TODO: Use DAO to get customers.
        Customer[] customers = {
                new Customer(1, "bobama", "Barack", "Obama"),
                new Customer(2, "hclinton", "Hillary", "Clinton"),
                new Customer(3, "makeamericagreatagain", "Donald", "Trump"),
                };
        request.setAttribute("customers", customers);
        return "customer-list.jsp";
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
