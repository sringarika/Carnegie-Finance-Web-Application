package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import cfs.model.Model;

public class CreateFundAction extends Action {

    public CreateFundAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "create-fund.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            // TODO: Maybe get the existing fund list for reference?
            return "create-fund.jsp";
        } else if (request.getMethod().equals("POST")) {
            // TODO        	
        	
        	if(request.getParameter("tickername").trim().length()==0 || request.getParameter("fundname").length() == 0) {
        		request.setAttribute("error", "Please provide a valid input");
                return "create-fund.jsp";
        	}
        	if (request.getParameter("tickername").trim().length() < 1 || request.getParameter("tickername").trim().length() > 5) {
        		request.setAttribute("error", "Ticker Name should be between 1-5 characters");
                return "create-fund.jsp";
            }
            request.setAttribute("message", "Fund created successfully!");
            return "success.jsp";
        } else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}




