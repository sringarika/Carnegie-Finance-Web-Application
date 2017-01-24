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
                new Customer("bobama", "Barack", "Obama", "1"),
                new Customer("hclinton", "Hillary", "Clinton", "1"),
                new Customer("makeamericagreatagain", "Donald", "Trump", "1"),
                };
        request.setAttribute("customers", customers);
        return "customer-list.jsp";
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
