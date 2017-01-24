package cfs.controller;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import cfs.databean.Customer;
import cfs.formbean.RequestCheckForm;
import cfs.model.Model;

public class RequestCheckAction extends Action {
	FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory.getInstance(RequestCheckForm.class);
    public RequestCheckAction(Model model) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "request-check.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        if (request.getMethod().equals("GET")) {
            // TODO: Get available cash. request.setAttribute("availableCash", cash)
            return "request-check.jsp";
        } else if (request.getMethod().equals("POST")) {
        	try {
				RequestCheckForm form = formBeanFactory.create(request);
				
				Customer customer = (Customer) request.getSession().getAttribute("customer");
				double amount = form.getRequestAmount();
				//TransactionDAO
			} catch (FormBeanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            request.setAttribute("message", "Check requested. It will be processed by the end of the business day.");
            return "success.jsp";
        } else {
            return null;
        }
    }

    @Override
    public AccessControlLevel getAccessControlLevel() {
        return AccessControlLevel.Customer;
    }
}
