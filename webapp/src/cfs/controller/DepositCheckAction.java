package cfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanFactory;

import cfs.formbean.ChangePasswordForm;
import cfs.formbean.DepositCheckForm;
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
    /*    int customerId;
        String customerIdStr = request.getParameter("customerId");
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
        	try {
                DepositCheckForm form = FormBeanFactory.getInstance(DepositCheckForm.class).create(request);
                List<String> validationErrors = form.getValidationErrors();
                if (validationErrors.size() > 0) {
                    throw new Exception(validationErrors.get(0));
                }
        	
            request.setAttribute("message", "Check deposited successfully! The transaction will be processed by the end of the business day.");
            return "confirmdepositcheck.jsp";
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            return "deposit-check.jsp";
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
