package cfs.controller;
//TODO Error check for create form
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanFactory;

import cfs.formbean.CreateFundForm;
import cfs.model.CustomerDAO;
import cfs.model.Model;
import cfs.model.TransactionDAO;

public class CreateFundAction extends Action {
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	
    public CreateFundAction(Model model) {
        customerDAO = model.getCustomerDAO();
        transactionDAO = model.getTransactionDAO();
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
        	try {
                CreateFundForm form = FormBeanFactory.getInstance(CreateFundForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    throw new Exception(validationErrors.get(0));
                }
            request.setAttribute("message", "Fund created successfully!");
            return "success.jsp";
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            return "create-fund.jsp";
        }
        }else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Employee;
    }
}




