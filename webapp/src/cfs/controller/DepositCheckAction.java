package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.model.Model;

public class DepositCheckAction extends Action {

    public DepositCheckAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "deposit-check.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        int customerId;
    /*    String customerIdStr = request.getParameter("customerId");
        try {
            customerId = Integer.parseInt(customerIdStr);
        } catch (Exception e) {
            request.setAttribute("error", "Invalid customerId!");
            return "error.jsp";
        }
        request.setAttribute("customerId", customerId);*/
        if (request.getMethod().equals("GET")) {
            // TODO: Get some information (e.g. name) for display using DAO.
            return "deposit-check.jsp";
        } else if (request.getMethod().equals("POST")) {
            // TODO
        	System.out.println(request.getParameter("amount"));
        
        	if(request.getParameter("amount").trim().length() == 0) {
    			request.setAttribute("error", "Amount cannot be left blank!");
                return "deposit-check.jsp";	
    		}
        	
        	double a = 0;
        	try {
        		a = Double.parseDouble(request.getParameter("amount"));
        		if(a < 0) {
        			request.setAttribute("error", "Amount must be greater than zero!");
                    return "deposit-check.jsp";	
        		}
        		if(a > Integer.MAX_VALUE) {
        			request.setAttribute("error", "Amount cannot be greater than $2147483647!");
                    return "deposit-check.jsp";	
        		}
        	} catch(NumberFormatException num){
                request.setAttribute("error", "Amount must be a valid number!");
                return "deposit-check.jsp";
        	}
        	
            request.setAttribute("message", "Check deposited successfully! The transaction will be processed by the end of the business day.");
            return "confirmdepositcheck.jsp";
        } else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}
