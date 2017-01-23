package cfs.formbean;

import java.util.ArrayList;
import java.util.List;

//import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class CreateFundForm extends FormBean{
	private String fund;
	private String ticker;
	private String button;
	
	/*public LoginForm(HttpServletRequest request) {
		username = request.getParameter("username");
		password = request.getParameter("password");
		button = request.getParameter("loginbutton");
	}*/
	
	public void setFund(String fund) {
		this.fund = fund;
	}
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	public void setButton(String button) {
		this.button = button;
	}
	
	public String getFund() {
		return fund;
	}
	
	public String getTicker() {
		return ticker;
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

		if (fund == null || fund.trim().length() == 0)
			errors.add("Fundname is required");
		
		if (ticker == null || ticker.trim().length() == 0)
			errors.add("Tickername is required");
    	
    	if (ticker.trim().length() < 1 || ticker.trim().length() > 5) 
    		errors.add("Ticker Name should be between 1-5 characters");
        
		if (errors.size() > 0)
			return errors;

		return errors;
	}
}
