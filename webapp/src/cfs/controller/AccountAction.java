package cfs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cfs.databean.Customer;
import cfs.databean.Position;
import cfs.model.CustomerDAO;
import cfs.model.CustomerPositionDAO;
import cfs.model.Model;

public class AccountAction extends Action {

	private CustomerDAO customerDAO;
	private CustomerPositionDAO customerPositionDAO;
	
    public AccountAction(Model model) {
    	customerDAO = model.getCustomerDAO();
    	customerPositionDAO = model.getCustomerPositionDAO();
    }

    @Override
    public String getName() {
        return "account.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	// if someone already logged in, switch to the account page
        if (session.getAttribute("customerId") == null) {
    		if (session.getAttribute("employeeId") != null) {
    		    return "employee.do";
    		} else {
    			return "login.do";
    		}
    	}
        try {
        	int customerId = (int) session.getAttribute("customerId");
        	Customer customer = customerDAO.read(customerId);
        	String firstName = customer.getFirstname();
        	String lastName = customer.getLastname();
        	String address = customer.getAddrLine1() + " " + customer.getAddrLine2() + " "
        	+ customer.getCity() + " " + customer.getState() + " " + customer.getZip();
        	double cash = customer.getCash();
        	request.setAttribute("firstName", firstName);
        	request.setAttribute("lastName", lastName);
        	request.setAttribute("address", address);
        	request.setAttribute("cash", cash);
        	Position[] positions = customerPositionDAO.findPositions(customerId);
        	request.setAttribute("positions", positions);
        	return "account.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            return "account.jsp";
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
