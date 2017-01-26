package cfs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cfs.databean.Customer;
import cfs.databean.Position;
import cfs.model.CustomerDAO;
import cfs.model.CustomerPositionDAO;
import cfs.model.Model;

public class AccountAction extends ViewCustomerAction {

	private CustomerDAO customerDAO;
	private CustomerPositionDAO customerPositionDAO;
	
    public AccountAction(Model model) {
        super(model);
    }

    @Override
    public String getName() {
        return "account.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        int customerId = (int) request.getSession().getAttribute("customerId");
        request.setAttribute("isMyAccount", true);
        return super.showCustomer(customerId, request);
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
