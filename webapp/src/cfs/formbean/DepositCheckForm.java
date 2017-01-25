package cfs.formbean;

import java.util.ArrayList;
import java.util.List;

//import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class DepositCheckForm extends FormBean{
	private String amount; 
	private String button;
	
	/*public LoginForm(HttpServletRequest request) {
		username = request.getParameter("username");
		password = request.getParameter("password");
		button = request.getParameter("loginbutton");
	}*/
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public void setButton(String button) {
		this.button = button;
	}
	
	public String getAmount() {
		return amount;
	}

	public String getButton() {
		return button;
	}

	public boolean isPresent() {		
		if(button == null) return false;
		return true;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (amount == null || amount.trim().length() == 0)
			errors.add("Amount is required");
        
		double a = 0;
    	try {
    		a = Double.parseDouble(amount);
    		if(a <= 0) {
    			errors.add("Amount must be greater than zero!");
    		}
    		
    		if(a > 1000000) {
    			errors.add("Amount cannot be greater than $1,000,000.00!");
    		}
    	} catch(NumberFormatException num){
            errors.add("Amount must be a valid number!");
    	}
    	

		if (errors.size() > 0)
			return errors;

		return errors;
	}
}
