package cfs.controller;
//TODO Error check for create form
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanFactory;

import cfs.databean.Fund;
import cfs.formbean.CreateFundForm;
import cfs.model.FundDAO;
import cfs.model.Model;

public class CreateFundAction extends Action {
	private FundDAO FundDAO;
	
    public CreateFundAction(Model model) {
        FundDAO = model.getFundDAO();
    }

    @Override
    public String getName() {
        return "create-fund.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
    	String fundname = request.getParameter("fund");
    	String ticker = request.getParameter("ticker");
    	Fund[] fn;
        if (request.getMethod().equals("GET")) {
			try {
				fn = FundDAO.match();
				if(fn == null) {
					request.setAttribute("message", "Currently we have no funds");
					return "create-fund.jsp";
				} 
				request.setAttribute("fundList", fn);
				return "create-fund.jsp";
			} catch (RollbackException e) {
				e.printStackTrace();
			}
            return "create-fund.jsp";

        } else if (request.getMethod().equals("POST")) {
            // TODO        	
        	System.out.println("POST");
        	try {
                CreateFundForm form = FormBeanFactory.getInstance(CreateFundForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                fn = FundDAO.match();
                request.setAttribute("fundList", fn);
                if (validationErrors.size() > 0) {
                    throw new Exception(validationErrors.get(0));
                }
				if(fn == null) {
					request.setAttribute("error", "Currently we have no funds");
					return "create-fund.jsp";
				}
				System.out.println("checkpoint1");
				System.out.println("looking for: "+fundname);
             Fund[] funame = FundDAO.fundName(fundname);
             System.out.println("before entering condition");
             if(funame != null && funame[0].getName().equals(fundname)) {
            	 System.out.println("funds exists");
               request.setAttribute("error", "Fund exists!");
			   return "create-fund.jsp";
             } 
             //not sure if ticker should be exclusive too
           /*  System.out.println("checkpoint2");
             Fund[] tiname = FundDAO.fundTicker(ticker);
             if(tiname != null && tiname[0].getName().equals(ticker)) {
            	 System.out.println("ticker exists");
            	 request.setAttribute("message", "Ticker exists!");
  			   return "create-fund.jsp";
             }*/
             System.out.println("checkpoint3");
            request.setAttribute("message", "Fund created successfully!");
            return "success.jsp";
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            return "create-fund.jsp";
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




